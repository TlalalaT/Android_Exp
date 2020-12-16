package com.example.final02

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.final02.News
import com.example.final02.R
import com.example.final02.test.imageDownloader
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.assist.ImageScaleType




internal class MyAdapter(context: Context, datas: List<News>) : BaseAdapter() {
    private val context: Context
    private val datas: List<News>
    private val options: DisplayImageOptions

    //定义三种类型
    private val THREE_IMAGE = 0
    private val TWO_IMAGE = 1
    private val ONE_IMAGE = 2
    override fun getCount(): Int {
        return datas.size
    }

    override fun getItem(position: Int) {
    }

    override fun getItemId(i: Int): Long {
        return 0
    }


    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View? {
        //得到当前条目对应的类型
        var view: View? = view
        val type = getItemViewType(i)
        if (type == THREE_IMAGE) {
            val viewHolder3: ViewHolder3
            if (view == null) {
                view = View.inflate(context, R.layout.item_three_image, null)
                viewHolder3 = ViewHolder3()
                viewHolder3.textView = view.findViewById(R.id.tv_title)
                viewHolder3.img1 = view.findViewById(R.id.img1)
                viewHolder3.img2 = view.findViewById(R.id.img2)
                viewHolder3.img3 = view.findViewById(R.id.img3)
                view.setTag(viewHolder3)
            } else {
                viewHolder3 = view.getTag() as ViewHolder3
            }
            viewHolder3.textView?.text = datas[i].title
            //下载图片
            imageDownloader.showImage(datas[i].thumbnail_pic_s,viewHolder3.img1)
            imageDownloader.showImage(datas[i].thumbnail_pic_s02,viewHolder3.img2)
            imageDownloader.showImage(datas[i].thumbnail_pic_s03,viewHolder3.img3)
            //ImageLoader.getInstance().displayImage(datas[i].thumbnail_pic_s, viewHolder3.img1, options)
            //ImageLoader.getInstance().displayImage(datas[i].thumbnail_pic_s02, viewHolder3.img2, options)
            //ImageLoader.getInstance().displayImage(datas[i].thumbnail_pic_s03, viewHolder3.img3, options)
            return view
        } else if (type == TWO_IMAGE) {
            val viewHolder2: ViewHolder2
            if (view == null) {
                view = View.inflate(context, R.layout.item_two_image, null)
                viewHolder2 = ViewHolder2()
                viewHolder2.textView = view.findViewById(R.id.tv_title)
                viewHolder2.img1 = view.findViewById(R.id.img1)
                viewHolder2.img2 = view.findViewById(R.id.img2)
                view.setTag(viewHolder2)
            } else {
                viewHolder2 = view.getTag() as ViewHolder2
            }
            viewHolder2.textView?.text = datas[i].title
            //下载图片
            imageDownloader.showImage(datas[i].thumbnail_pic_s,viewHolder2.img1)
            imageDownloader.showImage(datas[i].thumbnail_pic_s02,viewHolder2.img2)
            //ImageLoader.getInstance().displayImage(datas[i].thumbnail_pic_s, viewHolder2.img1, options)
            //ImageLoader.getInstance().displayImage(datas[i].thumbnail_pic_s02, viewHolder2.img2, options)
            return view
        } else {
            val viewHolder1: ViewHolder1
            if (view == null) {
                view = View.inflate(context, R.layout.item_one_image, null)
                viewHolder1 = ViewHolder1()
                viewHolder1.textView = view.findViewById(R.id.tv_title)
                viewHolder1.img1 = view.findViewById(R.id.img1)
                view.setTag(viewHolder1)
            } else {
                viewHolder1 = view.getTag() as ViewHolder1
            }
            viewHolder1.textView?.text = datas[i].title
            //下载图片
            imageDownloader.showImage(datas[i].thumbnail_pic_s,viewHolder1.img1)
            //ImageLoader.getInstance().displayImage(datas[i].thumbnail_pic_s, viewHolder1.img1, options)
            return view
        }
    }

    override fun getViewTypeCount(): Int {
        return 3
    }

    //position:条目的下标
    override fun getItemViewType(position: Int): Int {
        //得到图片地址
        val pics: String = datas[position].thumbnail_pic_s
        val pic02: String = datas[position].thumbnail_pic_s02
        val pic03: String = datas[position].thumbnail_pic_s03
        return if (pics != null && pic02 != null && pic03 != null) {
            THREE_IMAGE //三张图类型
        } else if (pics != null && pic02 != null && pic03 == null) {
            TWO_IMAGE //两张图的类型
        } else if (pics != null && pic02 == null && pic03 == null) {
            ONE_IMAGE //一张图的类型
        } else {
            ONE_IMAGE
        }
    }

    internal inner class ViewHolder1 {
        var textView: TextView? = null
        var img1: ImageView? = null
    }

    internal inner class ViewHolder2 {
        var textView: TextView? = null
        var img1: ImageView? = null
        var img2: ImageView? = null
    }

    internal inner class ViewHolder3 {
        var textView: TextView? = null
        var img1: ImageView? = null
        var img2: ImageView? = null
        var img3: ImageView? = null
    }

    init {
        this.context = context
        this.datas = datas
        options = DisplayImageOptions.Builder()
                .cacheInMemory(true) //使用内存缓存
                .cacheOnDisk(true) //使用磁盘缓存
                .showImageOnLoading(R.mipmap.ic_launcher) //设置正在下载的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher) //url为空或请求的资源不存在时
                .showImageOnFail(R.mipmap.ic_launcher) //下载失败时显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565) //设置图片色彩模式
                .imageScaleType(ImageScaleType.EXACTLY) //设置图片的缩放模式
                .build()
    }
}