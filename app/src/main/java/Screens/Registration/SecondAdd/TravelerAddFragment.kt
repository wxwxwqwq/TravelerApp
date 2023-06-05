package Screens.Registration.SecondAdd

import DataModels.TravelerModel
import ViewModels.DataTransactionViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.travelapp.*


class TravelerAddFragment() : Fragment()  {

    lateinit var bundle1: Bundle
    private lateinit var viewModel: DataTransactionViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_traveler_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRetainInstance(true)

        val CreateTravelBtn: Button = RegistrationActivity_context.findViewById(R.id.CreateTravelBtn)
        CreateTravelBtn.visibility = View.INVISIBLE

        val addTravelerBtn: Button = view.findViewById(R.id.Create)
        val backBtn: ImageView = view.findViewById(R.id.BackBtn)

        viewModel = ViewModelProvider(MainActivity_context).get(DataTransactionViewModel::class.java)

        val InputName: com.google.android.material.textfield.TextInputEditText = view.findViewById(R.id.InputName)
        val InputAge: com.google.android.material.textfield.TextInputEditText = view.findViewById(R.id.InputCalories)
        val ChooseGender: RadioGroup = view.findViewById(R.id.ChooseGender)
        val ChooseRestrictions: RadioGroup = view.findViewById(R.id.ChooseRestrictions)


        //Алгоритм изменения данных об участнике похода
        if(arguments?.getString("TravelerName") != null) {

            addTravelerBtn.visibility = View.INVISIBLE

            val changeTravelerButton: Button = view.findViewById(R.id.Change)
            changeTravelerButton.visibility = View.VISIBLE

            InputName.setText(arguments?.getString("TravelerName").toString())
            InputAge.setText(arguments?.getString("Age").toString())

            if (arguments?.getString("Gender").toString() == "Жен."){
                val Gender: RadioButton = view.findViewById(R.id.FemaleGender)
                Gender.isChecked = true
            }

            if (arguments?.getString("Restrictions").toString() == "Да"){
                val Restriction: RadioButton = view.findViewById(R.id.HaveRestriction)
                Restriction.isChecked = true
            }


            //Обработка нажатия на кнопку "Изменить"
            changeTravelerButton.setOnClickListener() {

                if (InputName.text.toString() == "" || InputAge.text.toString() == "") {
                    val Warning: TextView = view.findViewById(R.id.Warning)
                    Warning.visibility = View.VISIBLE
                }
                else {

                    //val str: String

                    //if (ChooseGender.checkedRadioButtonId == 2131362329) {
                    //    str = "Жен"
                    //} else {
                    //    str = "Муж"
                    //}

                    val selectedGenderRadioButton: RadioButton = view.findViewById(ChooseGender.checkedRadioButtonId)
                    val selectedRestrictionRadioButton: RadioButton = view.findViewById(ChooseRestrictions.checkedRadioButtonId)

                    viewModel.setChangedTravelerToAdapter.value = TravelerModel(
                        InputName.text.toString(),
                        InputAge.text.toString(),
                        selectedGenderRadioButton.getText().toString(),
                        selectedRestrictionRadioButton.getText().toString()
                    )

                    CreateTravelBtn.visibility = View.VISIBLE
                    addTravelerBtn.visibility = View.VISIBLE
                    changeTravelerButton.visibility = View.INVISIBLE

                    findNavController().navigate(R.id.action_travelerAddFragment_to_travelerListFragment)
                }
            }
        }

        //Обработка нажатия на кнопку "Добавить"
        addTravelerBtn.setOnClickListener {

            if (InputName.text.toString() == "" || InputAge.text.toString() == ""){
                val Warning: TextView = view.findViewById(R.id.Warning)
                Warning.visibility = View.VISIBLE
            }
            else {
                val trav: TravelerModel

                //if (InputRestrictions.text.toString()  == ""){
                //    InputRestrictions.setText("Нет")
                //}

                //if (ChooseGender.checkedRadioButtonId == 2131362329){
                //    trav = TravelerModel(
                //        InputName.text.toString(),
                //        InputAge.text.toString(),
                //        "Жен",
                //        InputRestrictions.text.toString()
                //    )
                //}
                //else{
                //    trav = TravelerModel(
                //        InputName.text.toString(),
                //        InputAge.text.toString(),
                //        "Муж",
                //        InputRestrictions.text.toString()
                //    )
                //}

                val selectedGenderRadioButton: RadioButton = view.findViewById(ChooseGender.checkedRadioButtonId)
                val selectedRestrictionRadioButton: RadioButton = view.findViewById(ChooseRestrictions.checkedRadioButtonId)

                trav = TravelerModel(
                    InputName.text.toString(),
                    InputAge.text.toString(),
                    selectedGenderRadioButton.getText().toString(),
                    selectedRestrictionRadioButton.getText().toString()
                )

                viewModel.addTravelerData.value = trav

                CreateTravelBtn.visibility = View.VISIBLE
                findNavController().navigate(R.id.action_travelerAddFragment_to_travelerListFragment)
            }
        }

        //Варианты нажатия на кнопку "Назад"
        if(arguments?.getString("TravelerName") != null) {

            //Обработка нажатия на кнопку "Назад"
            backBtn.setOnClickListener(){

                viewModel.setChangedTravelerToAdapter.value = TravelerModel(
                    arguments?.getString("TravelerName").toString(),
                    arguments?.getString("Age").toString(),
                    arguments?.getString("Gender").toString(),
                    arguments?.getString("Restrictions").toString()
                )

                CreateTravelBtn.visibility = View.VISIBLE
                findNavController().navigate(R.id.action_travelerAddFragment_to_travelerListFragment)
            }
        }
        else{

            //Обработка нажатия на кнопку "Назад"
            backBtn.setOnClickListener(){

                CreateTravelBtn.visibility = View.VISIBLE
                findNavController().navigate(R.id.action_travelerAddFragment_to_travelerListFragment)
            }
        }
    }
}
