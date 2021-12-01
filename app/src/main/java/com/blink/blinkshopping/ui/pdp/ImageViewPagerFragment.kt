package com.blink.blinkshopping.ui.pdp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.blink.blinkshopping.R
import com.bumptech.glide.Glide

class ImageViewPagerFragment(
    private var imageURI: String? = null,
    from: String
) : Fragment(){

    private var imgview: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView =
            inflater.inflate(R.layout.image_fragment_view_pager, container, false) as ViewGroup

        imgview = rootView.findViewById(R.id.imgview)

//        val args = arguments ?: throw AssertionError()
//        imageURI = args.getString(IMAGE_KEY)
        if (imageURI == null) throw AssertionError()

        imgview!!.visibility = View.VISIBLE
        Glide.with(activity!!).load(imageURI).into(imgview!!)
        //println("imageURI onStart $imageURI")

        return rootView
    }

    companion object {
        private const val IMAGE_KEY = "image_key"

//        fun newInstance(imageURL: String?): ImageViewPagerFragment {
//            val args = Bundle()
//            args.putString(IMAGE_KEY, imageURL)
//            val fragment = ImageViewPagerFragment()
//            fragment.arguments = args
//            return fragment
//        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        println("onAttach")
    }

    override fun onResume() {
        super.onResume()
        println("onResume")
    }

    override fun onStart() {
        super.onStart()
        println("onStart")
    }

    override fun onPause() {
        super.onPause()
        println("onPause")
    }

    override fun onDetach() {
        super.onDetach()
        println("onDetach")
    }

    override fun onStop() {
        super.onStop()
        println("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy")
    }


}