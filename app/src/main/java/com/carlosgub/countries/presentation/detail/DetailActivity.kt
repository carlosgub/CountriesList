package com.carlosgub.countries.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.carlosgub.countries.R
import com.carlosgub.countries.databinding.ActivityDetailBinding
import com.carlosgub.countries.domain.model.Country

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbDetailToolbar)

        val country = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_COUNTRY, Country::class.java)!!
        } else {
            intent.getParcelableExtra(EXTRA_COUNTRY)!!
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = country.name.common
        binding.tbDetailToolbar.setNavigationOnClickListener {
            finish()
        }



    }

    companion object {
        private const val EXTRA_COUNTRY = "extra_country"

        fun newIntent(context: Context, country: Country): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_COUNTRY, country)
            }
        }
    }
}

