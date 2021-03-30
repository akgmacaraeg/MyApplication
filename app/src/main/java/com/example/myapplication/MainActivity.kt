package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.api.GitHubApiService
import com.example.myapplication.api.SearchResult
import com.example.myapplication.api.createGitHubApiService
import com.example.myapplication.models.Repo
import com.example.myapplication.repodetails.RepoDetailsActivity
import com.example.myapplication.reposlist.ReposAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var adapter : ReposAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ReposAdapter {  repo ->
                //Toast.makeText(this, repo.name1, Toast.LENGTH_SHORT).show()
            RepoDetailsActivity.startActivity(this, repo)
        }

        val list: RecyclerView = findViewById(R.id.list)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter

        val service = createGitHubApiService()
        service.searchRepositories("android").enqueue(object : Callback<SearchResult> {
            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                //handle failure
            }

            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                val repos = response.body()?.items.orEmpty()
                adapter.submitList(repos)
            }




        })




        /*val sampleData = listOf(
            Repo("repo 1"),
            Repo("repo 2"),
            Repo("repo 3"),
            Repo("repo 4"),
            Repo("repo 5"),
            Repo("repo 6"),
            Repo("repo 7"),
            Repo("repo 8"),
            Repo("repo 9"),
            Repo("repo 10")
        )

        adapter.submitList(sampleData)
         */
    }
}
