package lk.applife.kidssafety.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import lk.applife.kidssafety.R;
import lk.applife.kidssafety.adapter.ArticlesAdapter;
import lk.applife.kidssafety.model.ArticleTitle;

import java.util.ArrayList;

public class ArticlesActivity extends AppCompatActivity {

    private static final String HOME_SAFETY = "HOME_SAFETY";
    private static final String SCHOOL_SAFETY = "SCHOOL_SAFETY";
    private static final String ROAD_SAFETY = "ROAD_SAFETY";
    RecyclerView articleRecyclerView;
    TextView tvArticlesScreenTitle;
    Button allArticleBackBtn;
    private ArrayList<ArticleTitle> articleArrayList;
    private ArticlesAdapter articleAdapter;
    String safetyType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_articles);

        tvArticlesScreenTitle = (TextView) findViewById(R.id.tvArticlesScreenTitle);
        allArticleBackBtn = (Button) findViewById(R.id.all_article_back_btn);
        articleRecyclerView = (RecyclerView) findViewById(R.id.articlesRecyclerView);
        articleRecyclerView.setLayoutManager( new LinearLayoutManager(this));
        articleArrayList = new ArrayList<>();
        articleAdapter = new ArticlesAdapter(this, articleArrayList);
        articleRecyclerView.setAdapter(articleAdapter);

        getIncomingData();

        allArticleBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getIncomingData() {
        if (getIntent().hasExtra("safety")){
            safetyType = getIntent().getStringExtra("safety");
            initializeData(safetyType);
        }
    }

    private void initializeData(String safetyType){
        switch (safetyType){
            case  HOME_SAFETY :
                tvArticlesScreenTitle.setText(R.string.homeSafetyText);
                articleArrayList.add(new ArticleTitle(1, getString(R.string.homeSafetyTitle1), "artHome1" , "Home Safety"));
                articleArrayList.add(new ArticleTitle(2, getString(R.string.homeSafetyTitle2), "artHome2", "Home Safety" ));
                articleArrayList.add(new ArticleTitle(3, getString(R.string.homeSafetyTitle3), "artHome3", "Home Safety" ));
                articleArrayList.add(new ArticleTitle(4, getString(R.string.homeSafetyTitle4), "artHome4", "Home Safety" ));
                articleArrayList.add(new ArticleTitle(5, getString(R.string.homeSafetyTitle5), "artHome5", "Home Safety" ));
                articleArrayList.add(new ArticleTitle(6, getString(R.string.homeSafetyTitle6), "artHome6", "Home Safety" ));
                articleArrayList.add(new ArticleTitle(7, getString(R.string.homeSafetyTitle7), "artHome7", "Home Safety" ));
                articleArrayList.add(new ArticleTitle(8, getString(R.string.homeSafetyTitle8), "artHome8", "Home Safety" ));
                articleAdapter.setArticles(articleArrayList);
                break;
            case SCHOOL_SAFETY :
                tvArticlesScreenTitle.setText(R.string.schoolSafetyText);
                articleArrayList.add(new ArticleTitle(1, getString(R.string.schoolSafetyTitle1), "artSchool1", "School Safety" ));
                articleArrayList.add(new ArticleTitle(2, getString(R.string.schoolSafetyTitle2), "artSchool2", "School Safety" ));
                articleArrayList.add(new ArticleTitle(3, getString(R.string.schoolSafetyTitle3), "artSchool3", "School Safety" ));
                articleArrayList.add(new ArticleTitle(4, getString(R.string.schoolSafetyTitle4), "artSchool4", "School Safety" ));
                articleArrayList.add(new ArticleTitle(5, getString(R.string.schoolSafetyTitle5), "artSchool5", "School Safety" ));
                articleArrayList.add(new ArticleTitle(6, getString(R.string.schoolSafetyTitle6), "artSchool6", "School Safety" ));
                articleArrayList.add(new ArticleTitle(7, getString(R.string.schoolSafetyTitle7), "artSchool7", "School Safety" ));
                articleAdapter.setArticles(articleArrayList);
                break;
            case ROAD_SAFETY :
                tvArticlesScreenTitle.setText(R.string.roadSafetyText);
                articleArrayList.add(new ArticleTitle(1, getString(R.string.roadSafetyTitle1), "artRoad1", "Road Safety" ));
                articleArrayList.add(new ArticleTitle(2, getString(R.string.roadSafetyTitle2), "artRoad2", "Road Safety" ));
                articleArrayList.add(new ArticleTitle(3, getString(R.string.roadSafetyTitle3), "artRoad3", "Road Safety" ));
                articleArrayList.add(new ArticleTitle(4, getString(R.string.roadSafetyTitle4), "artRoad4", "Road Safety" ));
                articleArrayList.add(new ArticleTitle(5, getString(R.string.roadSafetyTitle5), "artRoad5", "Road Safety" ));
                articleArrayList.add(new ArticleTitle(6, getString(R.string.roadSafetyTitle6), "artRoad6", "Road Safety" ));
                articleArrayList.add(new ArticleTitle(7, getString(R.string.roadSafetyTitle7), "artRoad7", "Road Safety" ));
                articleArrayList.add(new ArticleTitle(8, getString(R.string.roadSafetyTitle8), "artRoad8", "Road Safety" ));
                articleArrayList.add(new ArticleTitle(9, getString(R.string.roadSafetyTitle9), "artRoad9", "Road Safety" ));
                articleArrayList.add(new ArticleTitle(10, getString(R.string.roadSafetyTitle10), "artRoad10", "Road Safety" ));
                articleArrayList.add(new ArticleTitle(11, getString(R.string.roadSafetyTitle11), "artRoad11", "Road Safety" ));
                articleArrayList.add(new ArticleTitle(12, getString(R.string.roadSafetyTitle12), "artRoad12", "Road Safety" ));
                articleArrayList.add(new ArticleTitle(13, getString(R.string.roadSafetyTitle13), "artRoad13", "Road Safety" ));
                articleArrayList.add(new ArticleTitle(14, getString(R.string.roadSafetyTitle14), "artRoad14", "Road Safety" ));
                articleAdapter.setArticles(articleArrayList);
                break;
            default:
                Toast.makeText(this, getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
        }
    }
}
