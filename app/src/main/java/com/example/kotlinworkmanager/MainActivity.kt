package com.example.kotlinworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = Data.Builder()
            .putInt("data", 1).build()

        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .build()
/*
        val workRequest = androidx.work.OneTimeWorkRequestBuilder<RefreshData>()
            .setConstraints(constraints)
            .setInputData(data)
            //.setInitialDelay(5, TimeUnit.HOURS)
            //.addTag("myTag")
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)
*/

        val workRequest = androidx.work.PeriodicWorkRequestBuilder<RefreshData>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(data)
            //.setInitialDelay(5, TimeUnit.HOURS)
            //.addTag("myTag")
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)

        WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(workRequest.id)
            .observe(this, Observer { workInfo ->
                if (workInfo != null && workInfo.state.isFinished) {
                    println("workInfo.state.isFinished")
                }
            })

        //WorkManager.getInstance(this).cancelAllWork()
/*
        val oneTimeRequest : androidx.work.OneTimeWorkRequest = androidx.work.OneTimeWorkRequestBuilder<RefreshData>()
            .setConstraints(constraints)
            .setInputData(data)
            //.setInitialDelay(5, TimeUnit.HOURS)
            //.addTag("myTag")
            .build()

        WorkManager.getInstance(applicationContext).beginWith(oneTimeRequest).then(workRequest).enqueue() */
    }
}