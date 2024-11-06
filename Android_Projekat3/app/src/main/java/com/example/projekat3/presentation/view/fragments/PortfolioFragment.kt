package com.example.projekat3.presentation.view.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import androidx.fragment.app.Fragment
import com.example.projekat3.R
import com.example.projekat3.data.models.stocks.DetailedStock
import com.example.projekat3.data.models.stocks.LocalStockEntity
import com.example.projekat3.databinding.FragmentPortfolioBinding
import com.example.projekat3.presentation.contract.PortfolioContract
import com.example.projekat3.presentation.view.activities.DetailedStockActivity
import com.example.projekat3.presentation.view.recycler.adapter.PortfolioStockAdapter
import com.example.projekat3.presentation.view.states.PortfolioState
import com.example.projekat3.presentation.viewModel.PortfolioViewModel
import java.io.IOException
import java.io.InputStream
import java.time.LocalDate
import java.time.ZoneId
import kotlin.collections.ArrayList
import com.example.projekat3.data.models.stocks.GroupedStock
import com.example.projekat3.data.models.user.UserEntity
import java.util.*


class PortfolioFragment: Fragment(R.layout.fragment_portfolio)  {

    private val portfolioViewModel: PortfolioContract.ViewModel by sharedViewModel<PortfolioViewModel>()
    private var _binding: FragmentPortfolioBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PortfolioStockAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPortfolioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initView()
        loadUser()
        initRecycler()
        initObservers()
    }

    private fun initView(){
        binding.portChart.setBackgroundColor(Color.WHITE)
        binding.portChart.description.isEnabled = false
        binding.portChart.setDrawGridBackground(false)
        binding.portChart.isDragEnabled = true
        binding.portChart.setScaleEnabled(true)
        binding.portChart.setPinchZoom(true)
}


    //ovo se pokrene na pokretaju programa i zavisno od mode ili ucita starog usera ili ubaci novog u bazu
    //kada se ubaci novi u bazu u portfolioViewModelu se onda ucitaju njegovi podaci iz baze
    private fun loadUser(){
        val sharedPreferences = activity?.getSharedPreferences(activity?.packageName, AppCompatActivity.MODE_PRIVATE)

        val mode = sharedPreferences?.getString("mode", "").toString()
        val username = sharedPreferences?.getString("username", "").toString()
        val email = sharedPreferences?.getString("email", "").toString()
        val password = sharedPreferences?.getString("password", "").toString()

        if (mode == "LOGIN"){
            portfolioViewModel.getUserByNameMailPass(username, email, password)
        }
        else if (mode == "REGISTER"){
            portfolioViewModel.insertUser(UserEntity(0,username, email, password, 10000.0, 0.0))
        }
        sharedPreferences?.edit()?.putString("mode", "")?.apply()
    }


    private fun initObservers() {
        val entries: ArrayList<Entry> = ArrayList()


        portfolioViewModel.portfolioState.observe(viewLifecycleOwner) { newsState ->
            renderState(newsState)
        }

        /*
        kada se ovo updatuje mozemo da povucemo iz baze sve deonice koje on poseduje
        ne moze pre jer se nije id povukao iz baze

        te deonice se stave u userStocks -> observer ispod ovoga se onda aktivira i ucita se graf na osnovu tih podataka
         */
        portfolioViewModel.user.observe(viewLifecycleOwner) {
            if (portfolioViewModel.user.value?.id == 0L) Toast.makeText(context, "Please login as existing user", Toast.LENGTH_SHORT).show()
            portfolioViewModel.getAllStocksFromUserGrouped(portfolioViewModel.user.value!!.id)
            binding.userBalance.text = portfolioViewModel.user.value!!.balance.toString()
            binding.userPortfolioValue.text = portfolioViewModel.user.value!!.portfolioValue.toString()
        }

        //ucitavanje grafa




        portfolioViewModel.userStocks.observe(viewLifecycleOwner) {
            var i = 0
            var initialPortfolio = 0.0

            entries.clear()
            binding.portChart.invalidate()
            binding.portChart.clear()

            entries.add(Entry(i++.toFloat(), 0F))//stavljanje inicijalno da bude 0

            portfolioViewModel.userStocks.value!!.forEach {
                val value = it.value
                initialPortfolio += value * -1

                entries.add(Entry(i++.toFloat(), initialPortfolio.toFloat()))

                val lineDataSet = LineDataSet(entries, "")
                lineDataSet.color = Color.BLUE
                lineDataSet.setCircleColor(Color.GREEN)
                val data = LineData(lineDataSet)
                binding.portChart.data = data
                binding.portChart.invalidate()
            }

            if (portfolioViewModel.user.value?.id == 0L) {
                binding.userBalance.text = portfolioViewModel.user.value!!.balance.toString()
                binding.userPortfolioValue.text = portfolioViewModel.user.value!!.portfolioValue.toString()
            }
        }
    }

    private fun initRecycler(){
        adapter = PortfolioStockAdapter(::openDetailed)//on click metoda
        binding.portRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        binding.portRv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        binding.portRv.adapter = adapter
    }


    private fun openDetailed(localStock: GroupedStock){
        val myJson = activity?.resources?.openRawResource(R.raw.search_quote)//ucitavamo json file jer ne mozemo sa online baze
            ?.let { inputStreamToString(it) }
        if (myJson != null) {
            portfolioViewModel.searchStock(myJson)
            startDetailedActivity(portfolioViewModel.detailedStock)
        }
    }

    fun startDetailedActivity(detailedStock: DetailedStock?) {
        val intent = Intent(activity, DetailedStockActivity::class.java)
        intent.putExtra("detailedStock", detailedStock)
        intent.putExtra("numberOfOwned",getAmountOfClickedStock(detailedStock!!.name))
        intent.putExtra("balance", portfolioViewModel.user.value!!.balance)
        doAction.launch(intent)
    }

    private val doAction: ActivityResultLauncher<Intent> = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data

            val dateNow: Date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())//datum za deonice

            /*
            primamo broj kupljenih/prodatih i balanc dobijen/izgubljen
            moze da sve ide kroz istu funkciju jer su namesteni da budu -/+ u odnosu na buy/sell
             */
            val numberOfBought = data!!.getIntExtra("numberOfBought",0)
            val balanceSpent = data.getDoubleExtra("balanceSpent",0.0)
            val name = data.getStringExtra("name")
            val symbol = data.getStringExtra("symbol")

            //ako je buy onda cemo da na bazu dodamo + value
            //ako je sell na bazu dodajemo - value
            if (name != null && symbol != null) {
                portfolioViewModel.insertStock(
                    LocalStockEntity(
                        0,
                        portfolioViewModel.user.value!!.id,
                        name,
                        numberOfBought,
                        symbol,
                        dateNow.toString(),
                        balanceSpent
                    )
                )
                //u kolakni objekat usera stavljamo nove vrednosti
                portfolioViewModel.user.value!!.balance += balanceSpent
                portfolioViewModel.user.value!!.portfolioValue += balanceSpent * -1

                //na usera u bazi stavljamo nove vrednosti
                portfolioViewModel.updateUserBalance(
                    portfolioViewModel.user.value!!.id,
                    portfolioViewModel.user.value!!.balance,
                    portfolioViewModel.user.value!!.portfolioValue )
            }
        }
    }

    private fun inputStreamToString(inputStream: InputStream): String? {//za citanje json-a
        return try {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            String(bytes)
        } catch (e: IOException) {
            null
        }
    }

    /*
    kada se ucitava recycler napravim kopiju svih deonica i stavim je u amountOfOwned listu sa for each
    kada se funkcija  ispod , getAmountOfClickedStock, aktivira ona prodje kroz listu i nadje sve koje imaju ime deonice na koju smo kliknuli
    i vrati sumu, tj koliko imamo te deonice
     */
    private fun renderState(state: PortfolioState) {
        when (state) {
            is PortfolioState.StockSuccessGrouped -> {
                adapter.submitList(state.groupedStocks)

                portfolioViewModel.amountOfOwned.clear()

                state.groupedStocks.forEach {
                    portfolioViewModel.amountOfOwned.add(it)
                }
            }
            is PortfolioState.Error -> {
                println("ERROR")

                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is PortfolioState.DataFetched -> {
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    //objasnjenej linija 228
    private fun getAmountOfClickedStock(name: String): Int{
        portfolioViewModel.amountOfOwned.forEach{
            if (it.name == name) return it.sum
        }
        return 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}