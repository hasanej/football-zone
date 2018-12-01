package id.hasaneljabir.footballzone.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

class CalendarHelper {

    companion object {
        private val TAG = "CalendarHelper"
        val CALENDARHELPER_PERMISSION_REQUEST_CODE = 99
        fun requestCalendarReadWritePermission(caller: Activity) {
            val permissionList = ArrayList<String>()

            if (ContextCompat.checkSelfPermission(
                    caller,
                    Manifest.permission.WRITE_CALENDAR
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionList.add(Manifest.permission.WRITE_CALENDAR)

            }

            if (ContextCompat.checkSelfPermission(
                    caller,
                    Manifest.permission.READ_CALENDAR
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionList.add(Manifest.permission.READ_CALENDAR)

            }

            if (permissionList.size > 0) {
                val permissionArray = arrayOfNulls<String>(permissionList.size)

                for (i in permissionList.indices) {
                    permissionArray[i] = permissionList[i]
                }

                ActivityCompat.requestPermissions(
                    caller,
                    permissionArray,
                    CALENDARHELPER_PERMISSION_REQUEST_CODE
                )
            }

        }

        fun haveCalendarReadWritePermissions(caller: Activity): Boolean {
            var permissionCheck = ContextCompat.checkSelfPermission(
                caller,
                Manifest.permission.READ_CALENDAR
            )

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                permissionCheck = ContextCompat.checkSelfPermission(
                    caller,
                    Manifest.permission.WRITE_CALENDAR
                )

                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    return true
                }
            }

            return false
        }

    }
}