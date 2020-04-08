package com.example.pimpmywed.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit


class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {

        val now = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
        val wed = ZonedDateTime.of(2020, 5, 16, 0, 0, 0,0, ZoneId.systemDefault())
        val timer = object: CountDownTimer(wed.toInstant().toEpochMilli() - now.toInstant().toEpochMilli(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val time = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())

                val days = ChronoUnit.DAYS.between(time, wed)
                val hours = ChronoUnit.HOURS.between(time, wed) - days * 24
                val minutes = ChronoUnit.MINUTES.between(time, wed) - days * 24 * 60 - hours * 60
                val seconds = ChronoUnit.SECONDS.between(time, wed) - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60


                value = String.format("%s %s %s %s", days.toString() + " Days\n\n", hours.toString() + " Hours\n\n", minutes.toString() + " Minutes\n\n", seconds.toString() + " Seconds")
            }

            override fun onFinish() {

            }
        }
        timer.start()
    }
    val text: LiveData<String> = _text
}