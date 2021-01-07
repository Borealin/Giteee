# Giteee

## LOGO

<img width="25%" src="logo.svg"/>



## Description

A gitee mobile client for Android, develop in Kotlin

A simple implementation for MVVM in kotlin

## Goal

+ [ ] Framework
  + [x] DI
    + [x] Repository
  + [ ] Net
    + [x] OAuth
    + [ ] Api
  + [ ] Local
    + [x] DataStore
    + [ ] Room
+ [ ] Pages
  + [x] Home navigation
    + [x] home rv+concat adpter
    + [x] notification rv+paging 
    + [x] profile for user/organization
      + [x] follow er/ing same with organization
      + [x] repo display vp2+paging
      + [x] repo same with home repo
      + [x] orga same with home orga
      + [x] star same with home repo
  + [x] Issues/Pr
    + [x] list tablayout+vp2+rv
  + [x] Organizations 
    + [x] list rv+paging 
    + [x] item same as profile
  + [x] Repositories
    + [x] list rv+paging
    + [ ] item rv+concat adapter+md previewer
    + [ ] issue/pr same as home issue/pr
    + [ ] watcher same as home orga
    + [ ] browse code may give up detail view
    + [ ] commit rv+paging
  + [x] Search may give up
    + [x] repo same as home repo
    + [x] issue same as home issue
    + [x] user same as home orga
+ [ ] Practical Implementation
  + [x] Shared item view
    + [x] user item view (orga foer/ing watcher search)
    + [x] repo item view (repo starred search)
    + [x] issue/pr item view (issue/pr notification)
  + [ ] Fragment
    + [x] Home
    + [x] Notification
    + [x] Profile
    + [x] Repo list
    + [ ] Repo detail
    + [x] Issue/Pr list
    + [ ] Issue/Pr detail
    + [x] User list
    + [x] Search Selection