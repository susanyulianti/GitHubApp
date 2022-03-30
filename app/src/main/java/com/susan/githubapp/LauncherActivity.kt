package com.susan.githubapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider

class LauncherActivity : AppCompatActivity(), View.OnClickListener {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore("setting")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        applyThemeFromSetting()
        val btn_onboard : Button =findViewById(R.id.button_start)
        btn_onboard.setOnClickListener(this)
    }
    override fun onClick(v: View?) {

        when (v?.id){
            R.id.button_start ->{
                val moveIntent = Intent(this@LauncherActivity, SplashScreenActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }

    private fun applyThemeFromSetting() {
        val pref = SettingGitHubAppPreferences.getGitHubInstanceApp(dataStore)
        val settingViewModel =
            ViewModelProvider(this, SettingViewModelFactory(pref))[SettingGitHubAppViewModel::class.java]

        settingViewModel.getSettingThemes().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}
