package com.carlosgub.countries.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
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

        Glide.with(this).load(country.flags.png).into(binding.ivDetailFlag)

        binding.tvDetailNameCommon.text = country.name.common
        binding.tvDetailNameOfficial.text = country.name.official

        binding.tvDetailCapital.text = country.capital?.firstOrNull() ?: "N/A"

        binding.tvDetailRegion.text = country.region ?: "N/A"

        binding.tvDetailSubRegion.text = country.subregion ?: "N/A"

        binding.tvDetailLanguages.text = country.languages?.values?.joinToString(", ") ?: "N/A"

        binding.tvDetailCurrencies.text =
            country.currencies?.values?.joinToString(", ") { it.name } ?: "N/A"

        binding.tvDetailPopulation.text = country.population.toString()

        binding.tvDetailCarDriverSide.text = country.car?.side ?: "N/A"

        Glide.with(this).load(country.coatOfArms?.png).into(binding.ivDetailCoatOfArms)
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

