package intro.android.cm_tp_wtd.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import intro.android.cm_tp_wtd.MainActivity
import intro.android.cm_tp_wtd.R
import java.util.Locale

class MainFragment : Fragment() {
    private lateinit var languageButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val loginButton = view.findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }

        val createAccountButton = view.findViewById<TextView>(R.id.createAccountButton)
        createAccountButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_registerFragment)
        }

        languageButton = view.findViewById(R.id.languageButton)
        languageButton.setOnClickListener {
            toggleLanguage()
        }

        updateTexts()
        return view
    }

    private fun toggleLanguage() {
        val currentLocale = resources.configuration.locales[0]
        val newLocale = if (currentLocale.language == "en") Locale("pt", "PT") else Locale("en", "US")

        saveLanguagePreference(newLocale.language)
        (activity as? MainActivity)?.updateLocaleAndRecreate(newLocale)
    }

    private fun updateTexts() {
        if (::languageButton.isInitialized) {
            languageButton.text = if (Locale.getDefault().language == "en") "EN" else "PT"
        }
    }

    private fun saveLanguagePreference(language: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("app_language", language)
            apply()
        }
    }
}
