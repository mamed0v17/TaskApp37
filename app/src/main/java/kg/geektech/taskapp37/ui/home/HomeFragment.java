package kg.geektech.taskapp37.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

import kg.geektech.taskapp37.App;
import kg.geektech.taskapp37.R;
import kg.geektech.taskapp37.databinding.FragmentHomeBinding;
import kg.geektech.taskapp37.interfaces.OnItemClickListener;
import kg.geektech.taskapp37.models.News;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NewsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter();
        List<News> list = App.getInstance().getDatabase().newsDao().getAll();
        adapter.addItems(list);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                News news = adapter.getItem(pos);
                openFragment(news);
            }

            @Override
            public void onLongClick(int pos) {

                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setPositiveButton("Удалить?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        News news = adapter.getItem(pos);
                        App.getInstance().getDatabase().newsDao().delete(news);
                        adapter.removeItem(news, pos);
                    }
                });
                @SuppressLint("InflateParams") ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.item_alert_dialog, null);
                builder.setView(constraintLayout);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fab.setOnClickListener(v -> {
            openFragment(null);
        });
        getParentFragmentManager().setFragmentResultListener("rk_news", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                News news = (News) result.getSerializable("news");
                Log.e("Home", "text= " + news.getTitle());
                adapter.addItem(news);
            }
        });
        getParentFragmentManager().setFragmentResultListener("rk_news_update", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                News news = (News) result.getSerializable("news");
                Log.e("Home", "text= " + news.getTitle());
                adapter.updateItem(news);
            }
        });
        initList();
    }

    private void initList() {
        binding.recyclerView.setAdapter(adapter);

    }

    private void openFragment(News news) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putSerializable("news", news);
        navController.navigate(R.id.newsFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_sort){
            getSortedList();
        }
        return super.onOptionsItemSelected(item);

    }

    private void getSortedList() {
        List<News> list = App.getInstance().getDatabase().newsDao().getAllSortedByTitle();
        adapter.addItems(list);
    }
}