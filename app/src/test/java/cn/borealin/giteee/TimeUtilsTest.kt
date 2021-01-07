/*
 * Created by Borealin (308704199deniel@gmail.com) on 2021/1/6 下午6:41
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2021/1/6 下午6:41
 */

package cn.borealin.giteee

import cn.borealin.giteee.utils.TimeUtils
import org.junit.Test

class TimeUtilsTest {
    @Test
    fun testTime() {
        val timeString = "2021-01-07T22:29:16+01:00"
        print(TimeUtils.fromTimeToRemaining(timeString))
    }
}