    package com.example.drinksmachine.kiosk

    import android.app.Activity
    import android.app.admin.DeviceAdminReceiver
    import android.app.admin.DevicePolicyManager
    import android.content.ComponentName
    import android.content.Context
    import android.content.Intent
    import android.os.Build
    import android.widget.Toast

    class MyDeviceAdminReceiver : DeviceAdminReceiver() {
        override fun onEnabled(context: Context, intent: Intent) {
            Toast.makeText(context, "Device Admin 已啟用", Toast.LENGTH_SHORT).show()
        }

        override fun onDisabled(context: Context, intent: Intent) {
            Toast.makeText(context, "Device Admin 已停用", Toast.LENGTH_SHORT).show()
        }
    }

    object KioskHelper {
        fun startKioskMode(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val dpm = activity.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
                val componentName = ComponentName(activity, MyDeviceAdminReceiver::class.java)

                if (dpm.isDeviceOwnerApp(activity.packageName)) {
                    dpm.setLockTaskPackages(componentName, arrayOf(activity.packageName))
                    activity.startLockTask()

                }
            }
        }

        fun stopKioskMode(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.stopLockTask()
            }
        }
    }
