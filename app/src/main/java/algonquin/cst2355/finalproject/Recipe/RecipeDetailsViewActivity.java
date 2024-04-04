package algonquin.cst2355.finalproject.Recipe;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class RecipeDetailsViewActivity extends ViewModel {

    public MutableLiveData<ArrayList<Recipe>> definitionList = new MutableLiveData<>();
}