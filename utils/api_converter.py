import lxml
from bs4 import BeautifulSoup
import requests
import os
from selenium import webdriver
from selenium.webdriver.remote.webdriver import WebDriver as RemoteWebDriver
import pickle

from time import sleep

base_url = 'https://gitee.com/api/v5/swagger'

type_translation = {
    'string': 'String',
    'integer': 'Int',
    'boolean': 'Boolean',
    'object': 'Char',
    'array': 'List'
}


def str_to_hump(text: str) -> str:
    arr = filter(None, text.lower().split('_'))
    res = ''
    j = 0
    for i in arr:
        if j == 0:
            res = i
        else:
            res = res + i[0].upper() + i[1:]
        j += 1
    return res


def get_response(soup: BeautifulSoup) -> list:
    key_list = []
    value_list = []
    for key in soup.find_all('span', class_='tree-view-item-key show-desc'):
        key_list.append(key.text)
    for value in soup.find_all('span', class_='tree-view-item-value tree-view-item-value-string show-desc'):
        value_list.append(value.text)
    res = []
    for item in zip(key_list, value_list):
        res.append(
            (item[0].replace("\n", "").strip().strip(':').strip('"'), item[1]))
    return res


def get_parameter(soup: BeautifulSoup) -> list:
    form = soup.find('form', class_='ivu-form ivu-form-label-right')
    tbody = form.table.tbody
    params = []
    for param in tbody.children:
        params.append((param.contents[0].text.strip(
            "*"), param.contents[8].text))
    return params


def search_page(url: str, s: requests.Session = None, driver: RemoteWebDriver = None) -> tuple:
    text = ""
    if s is not None:
        r = s.get(url)
        text = r.text
    elif driver is not None:
        driver.get(url)
        sleep(0.5)
        text = driver.page_source
    else:
        driver = webdriver.Chrome()
        driver.get(url)
        sleep(0.5)
        text = driver.page_source
        driver.close()
    soup = BeautifulSoup(text, 'lxml')
    method = soup.find('span', class_='method-area').text.lower()
    url = soup.find('p', class_='url').text
    res = get_response(soup)
    params = get_parameter(soup)
    return (method, url, params, res)


def save_to_kotlin_data(name: str, path: str, content: list, comment: str = None, package: str = 'cn.borealin.giteee.model.api'):
    l = []
    if comment != None:
        l.append('/*\n'+comment+'\n*/')
    l.append('{}.{}\n\n'.format(
        package, path.split("/")[0]))
    l.append('import android.os.Parcelable\nimport com.google.gson.annotations.Expose\nimport com.google.gson.annotations.SerializedName\nimport kotlinx.android.parcel.Parcelize\n\n')
    l.append('@Parcelize\ndata class {}(\n'.format(name))
    for line in content:
        name, type_raw = line
        l.append('\t@Expose\n\t@SerializedName({})\n\tvar {}: {},\n'.format(name.strip(),
                                                                            str_to_hump(name.strip().strip('"')), type_translation[type_raw.strip()]))
    l.append(') : Parcelable')
    with open(path, 'w+') as file:
        file.writelines(l)
        file.close()


def get_all_type(res_list: list) -> tuple:
    type_req_set = set()
    type_res_set = set()
    for api in res_list:
        for line in api[3][2]:
            type_res_set.add(line[1])
        for line in api[3][3]:
            type_req_set.add(line[1])
    return(type_req_set, type_res_set)


if __name__ == '__main__':
    if not os.path.exists('api_res_list'):
        l = {}
        s = requests.session()
        driver = webdriver.Chrome()
        # driver = webdriver.PhantomJS()
        driver.get(base_url)
        with open('api_list.html', 'r', encoding='UTF-8') as list_file:
            list_content = list_file.read()
            soup = BeautifulSoup(list_content, 'lxml')
            for tagList in soup.body.ul.children:
                if tagList != '\n':
                    sublist = []
                    print(tagList.p.span.text)
                    for child in tagList.ul.children:
                        if child != '\n':
                            sublist.append((child.li.text, child['href']))
                    l[tagList.p.span.text] = sublist
            list_file.close()
        res_list = []
        for tag in l.keys():
            folder_name_split = tag.split(" ")
            folder_name_split[0] = folder_name_split[0].lower()
            folder_name = "".join(folder_name_split)
            for sub_api in l[tag]:
                name = (sub_api[1][2:][0].upper()+sub_api[1]
                        [2:][1:]).replace('V5', '')
                path = folder_name+'/'+name+'Data.kt'
                url = base_url+sub_api[1]
                res = search_page(url, driver=driver)
                res_list.append((name, path, url, res))
        with open('api_res_list', 'wb') as f:
            pickle.dump(res_list, f)
            f.close()
        driver.close()
    else:
        with open('api_res_list', 'rb') as f:
            res_list = pickle.load(f)
            f.close()
    for api in res_list:
        if not os.path.exists(api[1].split('/')[0]):
            os.mkdir(api[1].split('/')[0])
        save_to_kotlin_data(api[0]+'Data', api[1], api[3][2])
