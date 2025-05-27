package com.example.onlinequiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {

    private Context context;
    private List<QuizItem> quizList;

    // 1️⃣ Define interface
    public interface OnItemClickListener {
        void onItemClick(QuizItem item);
    }

    private OnItemClickListener listener;

    // 2️⃣ Setter for the click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public QuizAdapter(Context context, List<QuizItem> quizList) {
        this.context = context;
        this.quizList = quizList;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_question, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        QuizItem q = quizList.get(position);
        holder.tvQuestion.setText(q.getQuestion());
        holder.tvChoices.setText(q.getChoices());
        holder.tvAnswer.setText("Answer: " + q.getAnswer());

        // 3️⃣ Set click listener here
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(q);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion, tvChoices, tvAnswer;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvChoices = itemView.findViewById(R.id.tvChoices);
            tvAnswer = itemView.findViewById(R.id.tvAnswer);
        }
    }
}
