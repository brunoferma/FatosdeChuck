package com.brunomf.fatosdechuck.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.brunomf.fatosdechuck.R
import com.brunomf.fatosdechuck.constants.Constants
import com.brunomf.fatosdechuck.databinding.ActivityMainBinding
import com.brunomf.fatosdechuck.service.utils.CheckNetworkConnection.isOnline
import com.brunomf.fatosdechuck.view.adapter.FactsAdapter
import com.brunomf.fatosdechuck.view.listener.FactsListener
import com.brunomf.fatosdechuck.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: FactsAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mListener: FactsListener
    private var mUrlMessage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        observe()
        setListeners()
        mAdapter.attachListener(mListener)
    }

    private fun checkConnection() {
        if (isOnline(this)) {
            checkBundle()
        } else {
            showAlert(R.string.error_lost_connection)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isOnline(this)) {
            checkBundle()
        } else {
            showAlert(R.string.error_lost_connection)
        }
    }

    private fun checkBundle() {
        val bundle = intent.extras
        if (bundle != null && mUrlMessage == "") {
            setExtras(bundle)
        } else if (bundle != null && mUrlMessage != "") {
            mUrlMessage = ""
        } else {
            binding.viewFlipperFacts.displayedChild = VIEW_HELLO_HELP
        }
    }

    private fun setExtras(bundle: Bundle) {
        val message = bundle.getString(Constants.SEARCH_MESSAGE)
        val isSearchByRandom = bundle.getBoolean(Constants.IS_SEARCH_BY_RANDOM)
        val isSearchByCategory = bundle.getBoolean(Constants.IS_SEARCH_BY_CATEGORY)

        checkExtras(isSearchByCategory, isSearchByRandom, message)
    }

    private fun checkExtras(
        isSearchByCategory: Boolean,
        isSearchByRandom: Boolean,
        message: String?
    ) {
        when {
            !isSearchByCategory && !isSearchByRandom -> {
                if (message != null) {
                    mViewModel.getByFreeSearch(message)
                    binding.viewFlipperFacts.displayedChild = MY_PROGRESSBAR
                } else {
                    binding.textHelp.text = getString(R.string.title_new_search_question)
                    binding.viewFlipperFacts.displayedChild = VIEW_HELLO_HELP
                }
            }
            isSearchByRandom -> {
                mViewModel.getByRandom()
                binding.viewFlipperFacts.displayedChild = MY_PROGRESSBAR
            }
            isSearchByCategory -> {
                if (message != null) {
                    mViewModel.getByCategory(message)
                    binding.viewFlipperFacts.displayedChild = MY_PROGRESSBAR
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                navigateToSearch()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToSearch() {
        startActivity(Intent(this, SearchActivity::class.java))
        finish()
    }

    private fun observe() {
        mViewModel.searchResultLiveData.observe(this, Observer {
            it?.let { facts ->
                mAdapter.setFacts(facts)
            }
        })

        mViewModel.viewFlipperLiveData.observe(this, Observer {
            it?.let { viewFlipper ->
                if (viewFlipper.first == Constants.VIEW_FLIPPER_SEARCH_IS_EMPTY) {
                    binding.imageError.setImageResource(R.drawable.ic_baseline_search_off_24)
                    binding.viewFlipperFacts.displayedChild = Constants.VIEW_FLIPPER_ERROR
                } else {
                    binding.viewFlipperFacts.displayedChild = viewFlipper.first
                    binding.imageError.setImageResource(R.drawable.ic_baseline_error_50)
                }
                viewFlipper.second?.let { errorMessageResId ->
                    binding.textViewError.text = getString(errorMessageResId)
                }
            }
        })

        mViewModel.connectionErrorLiveData.observe(this, Observer {
            showAlert(it.first)
        })
    }

    private fun setView() {
        setAdapter()
        setHelloImage()
    }

    private fun setHelloImage() {
        val welcomeImage = binding.welcomeImage
        Glide.with(this)
            .load("https://www.pngkey.com/png/detail/100-1007129_chuck-norris-approved-approved-by-chuck-norris.png")
            .into(welcomeImage);
    }

    private fun setAdapter() {
        mAdapter = FactsAdapter(this@MainActivity)
        mRecyclerView = binding.root.findViewById(R.id.facts_recycler)
        mRecyclerView.layoutManager = LinearLayoutManager(
            this@MainActivity, LinearLayoutManager.VERTICAL, false
        )
        mRecyclerView.adapter = mAdapter
    }

    private fun setListeners() {
        mListener = object : FactsListener {
            override fun onClickShareImage(url: String) {
                val sendIntent: Intent = Intent().apply {
                    mUrlMessage = url
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, mUrlMessage)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, "Share URL of this Joke!")
                startActivity(shareIntent)
            }
        }
    }

    private fun showAlert(errorMsg: Int) {
        AlertDialog.Builder(this)
            .setTitle(R.string.error_alert)
            .setMessage(errorMsg)
            .setPositiveButton(R.string.action_try_again) { _, _ ->
                checkConnection()
            }
            .show()
    }

    companion object {
        private const val VIEW_HELLO_HELP = 0
        private const val MY_PROGRESSBAR = 1
    }
}
