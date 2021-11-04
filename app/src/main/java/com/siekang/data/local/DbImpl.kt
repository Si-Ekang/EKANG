package com.siekang.data.local


import com.siekang.data.local.dao.UserDao
import javax.inject.Inject

class DbImpl @Inject constructor(
    userDao: UserDao/*,
    weatherDao: WeatherDao*/
) : IDb {

    private var mUserDao: UserDao = userDao

    override fun deleteAll() {
        //mWeatherDao.deleteAll()
    }
}