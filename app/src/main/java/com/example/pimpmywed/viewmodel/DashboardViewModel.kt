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
        val wed = ZonedDateTime.of(2020, 11, 14, 0, 0, 0,0, ZoneId.systemDefault())
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

//    val days: LiveData<String> = liveData {
//        emit(_text.value?.days.toString())
//    }
//
//    val hours: LiveData<String> = liveData {
//        emit(_text.value?.hours.toString())
//    }
//
//    val minutes: LiveData<String> = liveData {
//        emit(_text.value?.minutes.toString())
//    }
//
//    val seconds: LiveData<String> = liveData {
//        emit(_text.value?.seconds.toString())
//    }






//    val timerFlow : Flow<Timestamp> = flow{
//        val now = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
//        val wed = ZonedDateTime.of(2020, 5, 16, 0, 0, 0,0, ZoneId.systemDefault())
//        val timer = object: CountDownTimer(wed.toInstant().toEpochMilli() - now.toInstant().toEpochMilli(), 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                val time = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
//
//                val days = ChronoUnit.DAYS.between(time, wed)
//                val hours = ChronoUnit.HOURS.between(time, wed) - days * 24
//                val minutes = ChronoUnit.MINUTES.between(time, wed) - days * 24 * 60 - hours * 60
//                val seconds = ChronoUnit.SECONDS.between(time, wed) - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60
//
//                val timestamp = Timestamp(days, hours, minutes, seconds)
////                viewModelScope.launch {
//                emit(timestamp)
////                }
//
////                value = String.format("%s %s %s %s", days.toString() + " Days\n\n", hours.toString() + " Hours\n\n", minutes.toString() + " Minutes\n\n", seconds.toString() + " Seconds")
//            }
//
//            override fun onFinish() {
//
//            }
//        }
//        timer.start()
//    }


//    val days: LiveData<String> = liveData {
//        timerFlow.collect{ emit(it.days.toString())}
//    }
//
//    val hours: LiveData<String> = liveData {
//        timerFlow.collect{ emit(it.hours.toString())}
//    }
//
//    val minutes: LiveData<String> = liveData {
//        timerFlow.collect{ emit(it.minutes.toString())}
//    }
//
//    val seconds: LiveData<String> = liveData {
//        timerFlow.collect{ emit(it.seconds.toString())}
//    }
//    }
}