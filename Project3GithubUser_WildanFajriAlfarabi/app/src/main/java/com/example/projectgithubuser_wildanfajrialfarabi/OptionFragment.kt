package com.example.projectgithubuser_wildanfajrialfarabi

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectgithubuser_wildanfajrialfarabi.databinding.FragmentOptionBinding

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "option")

class OptionFragment : Fragment() {

    private lateinit var binding: FragmentOptionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentOptionBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pref = activity?.let { OptionPreferences.getInstance(it.dataStore) }
        val preferencesViewModel = ViewModelProvider(
            this, PreferenceViewModelFactory(pref!!)
        )[PreferencesViewModel::class.java]
        val switchTheme = binding.switchTheme
        //preferencesViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            //switchTheme.isChecked = isDarkModeActive
        //}
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            preferencesViewModel.saveThemeSetting(isChecked)
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }
    }
}