package com.example.kotlinworkmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class RefreshData (val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val getData = inputData
        val myNumber = getData.getInt("data", 0)
        refreshData(myNumber)
        return Result.success()
    }

    private fun refreshData(myNumber : Int) {
        val sharedpreferences = context.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        var mySavedNumber = sharedpreferences.getInt("mySavedNumber", 0)
        mySavedNumber = mySavedNumber + myNumber
        println("mySavedNumber: $mySavedNumber")
        sharedpreferences.edit().putInt("mySavedNumber", mySavedNumber).apply()
    }
}