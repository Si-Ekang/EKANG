package com.siekang.data

import com.siekang.data.local.IDb
import com.siekang.data.remote.IApi

interface IRepository : IDb, IApi {

}