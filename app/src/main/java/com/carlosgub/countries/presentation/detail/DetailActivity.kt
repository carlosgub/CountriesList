package com.carlosgub.countries.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.carlosgub.countries.databinding.ActivityDetailBinding
import com.carlosgub.countries.domain.model.Country
import java.text.NumberFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbDetailToolbar)

        val country: Country = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_COUNTRY, Country::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_COUNTRY)!!
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = country.name.common
        binding.tbDetailToolbar.setNavigationOnClickListener {
            finish()
        }

        detailContent(country)
    }

    private fun detailContent(country: Country) {
        Glide.with(this).load(country.flags.png).into(binding.ivDetailFlag)

        detailCountryName(country.name)

        detailCapital(country.capital?.firstOrNull())

        detailRegion(country.region)

        detailSubRegion(country.subregion)

        detailLanguages(country.languages?.values)

        detailCurrencies(country.currencies?.values)

        detailPopulation(country.population)

        detailCarDriversSide(country.car?.side)

        detailCoatOfArms(country.coatOfArms)
    }

    private fun detailCountryName(name: Country.CountryName) {
        binding.tvDetailNameCommon.text = name.common
        binding.tvDetailNameOfficial.text = name.official
    }

    private fun detailCapital(capital: String?) {
        binding.tvDetailCapital.text = capital ?: "N/A"
    }

    private fun detailRegion(region: String?) {
        binding.tvDetailRegion.text = region ?: "N/A"
    }

    private fun detailSubRegion(subRegion: String?) {
        binding.tvDetailSubRegion.text = subRegion ?: "N/A"
    }

    private fun detailLanguages(languages: Collection<String>?) {
        binding.tvDetailLanguages.text = languages?.joinToString(", ") ?: "N/A"
    }

    private fun detailCurrencies(currencies: Collection<Country.CountryCurrency>?) {
        binding.tvDetailCurrencies.text = currencies?.joinToString(", ") { it.name } ?: "N/A"
    }

    private fun detailPopulation(population: Long) {
        binding.tvDetailPopulation.text =
            NumberFormat.getInstance(Locale.getDefault()).format(population)
    }

    private fun detailCarDriversSide(side: String?) {
        binding.tvDetailCarDriverSide.text = side ?: "N/A"
    }

    private fun detailCoatOfArms(coatOfArms: Country.CountryCoatOfArms?) {
        Glide.with(this).load(coatOfArms?.png).into(binding.ivDetailCoatOfArms)
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

