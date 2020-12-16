package com.example.final02

import android.app.FragmentManager
import android.app.FragmentTransaction
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem


class MainActivity : AppCompatActivity(),BottomNavigationBar.OnTabSelectedListener {
    private var bottomNavigationBar: BottomNavigationBar? = null
    var lastSelectedPosition = 0
    private val TAG = MainActivity::class.java.simpleName
    private val mNewsFragment: NewsFragment? = null
    private val mWeatherFragment: WeatherFragment? = null
    private val mMineFragment: MineFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationBar = findViewById<View>(R.id.bottom_navigation_bar) as BottomNavigationBar

        bottomNavigationBar!!
            .setTabSelectedListener(this)
            .setMode(BottomNavigationBar.MODE_FIXED)


        /** 添加导航按钮 */
        /** 添加导航按钮  */
        bottomNavigationBar!!
            .addItem(BottomNavigationItem(R.drawable.news_top, "新闻"))
            .addItem(BottomNavigationItem(R.drawable.movie, "电影"))
            .addItem(BottomNavigationItem(R.drawable.weather_top, "天气"))
            .addItem(BottomNavigationItem(R.drawable.mine_top, "我的"))
            .setFirstSelectedPosition(lastSelectedPosition)
            .initialise() //initialise 一定要放在 所有设置的最后一项


        setDefaultFragment() //设置默认导航栏


    }
    private fun setDefaultFragment() {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.tabs, NewsFragment())
        transaction.commit()
    }

    /**
     * 设置导航选中的事件
     */
    override fun onTabSelected(position: Int) {
        Log.d(TAG, "onTabSelected() called with: position = [$position]")
        val fm: FragmentManager = this.fragmentManager
        //开启事务
        val transaction: FragmentTransaction = fm.beginTransaction()
        when (position) {
            0 -> {
                if (mNewsFragment == null) {
                    val fragmentManager = supportFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.tabs, NewsFragment())
                    transaction.commit()
                }
                //transaction.replace(R.id.tabs, mNewsFragment)
            }
            1 -> {
                if (mWeatherFragment == null) {
                    val fragmentManager = supportFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.tabs, MovieFragment())
                    transaction.commit()
                }
                //transaction.replace(R.id.tb, mScanFragment)
            }
            2 -> {
                if (mWeatherFragment == null) {
                    val fragmentManager = supportFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.tabs, WeatherFragment())
                    transaction.commit()
                }
                //transaction.replace(R.id.tb, mScanFragment)
            }
            3 -> {
                if (mMineFragment == null) {
                    val fragmentManager = supportFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.tabs, MineFragment())
                    transaction.commit()
                }
                //transaction.replace(R.id.tb, mMyFragment)
            }
            else -> {
            }
        }
        transaction.commit() // 事务提交
    }

    /**
     * 设置未选中Fragment 事务
     */
    override fun onTabUnselected(position: Int) {}

    /**
     * 设置释放Fragment 事务
     */
    override fun onTabReselected(position: Int) {}

}