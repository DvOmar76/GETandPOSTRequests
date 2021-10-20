package com.example.getandpostrequests

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val apiInterface=APIClient().getData()?.create(APIInterface::class.java)
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchData()
        btnAdduser.setOnClickListener {
            startActivity(Intent(applicationContext,AddUser::class.java))
        }
    }


    override fun onRestart() {
        super.onRestart()
        fetchData()
    }
    fun fetchData(){
        funProgressDialog()
        if (apiInterface!=null){
            apiInterface.getData().enqueue(object : Callback<Data?> {
                override fun onResponse(call: Call<Data?>, response: Response<Data?>) {
                  progressDialog.dismiss()
                    val users=response.body()!!
                    var text=""
                    for (user in users){
                        text+="name: ${user.name}\n pk${user.pk}\n\n"
                    }
                    textView.text=text
                }

                override fun onFailure(call: Call<Data?>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Error ", Toast.LENGTH_SHORT).show()

                }
            })
        }
    }
    fun funProgressDialog(){
        progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()
    }
}