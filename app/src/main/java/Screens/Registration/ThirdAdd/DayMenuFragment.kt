package Screens.Registration.ThirdAdd

import ViewModels.ThirdAdd.MenuAddViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.travelapp.R


class DayMenuFragment(day: Int) : Fragment() {

    val dayNum: Int = day

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val TextField: TextView = view.findViewById(R.id.text)

        TextField.text = dayNum.toString()
    }
}