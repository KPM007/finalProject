package algonquin.cst2355.finalproject.Recipe;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2355.finalproject.Recipe.Recipe;

public class RecipeDetailsViewActivity extends ViewModel {

    public MutableLiveData<ArrayList<Recipe>> definitionList = new MutableLiveData<>();
}