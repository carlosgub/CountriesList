package com.carlosgub.countries.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.carlosgub.countries.R
import com.carlosgub.countries.domain.model.Country

class DetailActivity : AppCompatActivity() {
    companion object{
        private const val EXTRA_COUNTRY = "extra_country"

        fun newIntent(context: Context, country: Country): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_COUNTRY, country)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.tbDetailToolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }
}
