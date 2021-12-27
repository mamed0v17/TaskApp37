package kg.geektech.taskapp37;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kg.geektech.taskapp37.databinding.ItemBoardBinding;
import kg.geektech.taskapp37.interfaces.OnStartClickListener;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private String[] titles = new String[]{"Добро пожаловать!", "Вас приветствует компания |ММ|", "Грузинские мандарины"};
    private String[] speech = new String[]{"Мы рады что вы оказались именно тут!!!","Крупнейшия компания в Кыргызстане!","Без косточек!"};
    private int[] image = new int[]{R.drawable.welcome_image,R.drawable.company_mm,R.drawable.mandariny};
    private Context context;

    private OnStartClickListener clickListener;
    @NonNull
    @Override
    public BoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(ItemBoardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BoardAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public void setClickListener(OnStartClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemBoardBinding binding;

        public ViewHolder(@NonNull ItemBoardBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Prefs(context).saveBoardState();
                    clickListener.OnStartClick();
                }
            });
        }

        public void bind(int position) {
            binding.textTitle.setText(titles[position]);
            binding.imageView.setImageResource(image[position]);
            binding.textDesc.setText(speech[position]);
            if (position == titles.length - 1 ){
                binding.btnStart.setVisibility(View.VISIBLE);
            }else{
                binding.btnStart.setVisibility(View.INVISIBLE);
            }
        }
    }
}

