package com.example.pimpmywed.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pimpmywed.model.Timestamp
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<Timestamp>().apply {

        val now = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
        val wed = ZonedDateTime.of(2021, 10, 9, 0, 0, 0,0, ZoneId.systemDefault())
        val timer = object: CountDownTimer(wed.toInstant().toEpochMilli() - now.toInstant().toEpochMilli(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val time = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())

                val days = ChronoUnit.DAYS.between(time, wed)
                val hours = ChronoUnit.HOURS.between(time, wed) - days * 24
                val minutes = ChronoUnit.MINUTES.between(time, wed) - days * 24 * 60 - hours * 60
                val seconds = ChronoUnit.SECONDS.between(time, wed) - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60

                value = Timestamp(days.toString(), hours.toString(), minutes.toString(), seconds.toString())
            }

            override fun onFinish() {

            }
        }
        timer.start()
    }

    val text: LiveData<Timestamp> = _text
}