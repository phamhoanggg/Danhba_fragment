package com.example.danhba_fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.danhba_fragment.User
import com.example.danhba_fragment.UserAdapter
import com.example.danhba_fragment.R


class ListPhoneBookFragment: Fragment() {
    companion object {
        const val DETAIL_FRAGMENT_CODE = "DETAIL_FRAGMENT_CODE"

    }

    private val userList: ArrayList<User> = arrayListOf()
    private lateinit var navigateToAddUserListener: NavigateToAddUserListener
    private lateinit var mAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repeat(5) {
            userList.add(
                User(
                    it,
                    "Hoang",
                    "0123456789",
                    "hoang@gmail.com"
                )
            )
        }
        repeat(5) {
            userList.add(
                User(
                    it,
                    "Pham Hoang",
                    "0987654321",
                    "nv@gmail.com"
                )
            )
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list_phone_book, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = UserAdapter(userList).apply {
            onClickItem = {
                navigateToUserDetail(it)
            }
        }

        view.findViewById<RecyclerView>(R.id.rlvListPhoneBook).apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.create ->{
                navigateToAddUser()
                true
            }
            else -> false
        }
        return true
    }

    fun addUser(user: User){
        userList.add(user)
        mAdapter.notifyDataSetChanged()
    }

    fun setNavigateToAddUserListener(navigateToAddUserListener: NavigateToAddUserListener){
        this.navigateToAddUserListener = navigateToAddUserListener
    }

    fun getListSize(): Int{
        return userList.size
    }


    private fun navigateToUserDetail(user: User) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        val bundle = Bundle();
        bundle.putParcelable(DETAIL_FRAGMENT_CODE, user)
        val detailFragment = DetailFragment()
        detailFragment.arguments = bundle
        fragmentTransaction.replace(R.id.frameLayout, detailFragment).addToBackStack("DetailFragment").commit()
    }
    private fun navigateToAddUser() {
        navigateToAddUserListener.navigateToAddUser()
    }

    interface NavigateToAddUserListener{
        fun navigateToAddUser()
    }
}