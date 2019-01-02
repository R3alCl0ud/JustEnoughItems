package mezz.jei.ingredients;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IIngredientType;

public class Ingredients implements IIngredients {
	private final Map<IIngredientType, List<List>> inputs = new IdentityHashMap<>();
	private final Map<IIngredientType, List<List>> outputs = new IdentityHashMap<>();

	@Override
	public <T> void setInput(IIngredientType<T> ingredientType, T input) {
		setInputs(ingredientType, Collections.singletonList(input));
	}

	@Override
	public <T> void setInputLists(IIngredientType<T> ingredientType, List<List<T>> inputs) {
		List<List> expandedInputs = new ArrayList<>(inputs);
		this.inputs.put(ingredientType, expandedInputs);
	}

	@Override
	public <T> void setInputs(IIngredientType<T> ingredientType, List<T> inputs) {
		List<List> expandedInputs = new ArrayList<>();
		for (T input : inputs) {
			List<T> expandedInput = Collections.singletonList(input);
			expandedInputs.add(expandedInput);
		}
		this.inputs.put(ingredientType, expandedInputs);
	}

	@Override
	public <T> void setOutput(IIngredientType<T> ingredientType, T output) {
		setOutputs(ingredientType, Collections.singletonList(output));
	}

	@Override
	public <T> void setOutputs(IIngredientType<T> ingredientType, List<T> outputs) {
		List<List> expandedOutputs = new ArrayList<>();
		for (T output : outputs) {
			List<T> expandedOutput = Collections.singletonList(output);
			expandedOutputs.add(expandedOutput);
		}

		this.outputs.put(ingredientType, expandedOutputs);
	}

	@Override
	public <T> void setOutputLists(IIngredientType<T> ingredientType, List<List<T>> outputs) {
		List<List> expandedOutputs = new ArrayList<>(outputs);
		this.outputs.put(ingredientType, expandedOutputs);
	}

	@Override
	public <T> List<List<T>> getInputs(IIngredientType<T> ingredientType) {
		@SuppressWarnings("unchecked")
		List<List<T>> inputs = (List<List<T>>) (Object) this.inputs.get(ingredientType);
		if (inputs == null) {
			return Collections.emptyList();
		}
		return inputs;
	}

	@Override
	public <T> List<List<T>> getOutputs(IIngredientType<T> ingredientType) {
		@SuppressWarnings("unchecked")
		List<List<T>> outputs = (List<List<T>>) (Object) this.outputs.get(ingredientType);
		if (outputs == null) {
			return Collections.emptyList();
		}
		return outputs;
	}

	public Map<IIngredientType, List> getInputIngredients() {
		Map<IIngredientType, List> inputIngredients = new IdentityHashMap<>();
		for (Map.Entry<IIngredientType, List<List>> entry : inputs.entrySet()) {
			List<Object> flatIngredients = new ArrayList<>();
			for (List ingredients : entry.getValue()) {
				flatIngredients.addAll(ingredients);
			}
			inputIngredients.put(entry.getKey(), flatIngredients);
		}
		return inputIngredients;
	}

	public Map<IIngredientType, List> getOutputIngredients() {
		Map<IIngredientType, List> outputIngredients = new IdentityHashMap<>();
		for (Map.Entry<IIngredientType, List<List>> entry : outputs.entrySet()) {
			List<Object> flatIngredients = new ArrayList<>();
			for (List ingredients : entry.getValue()) {
				flatIngredients.addAll(ingredients);
			}
			outputIngredients.put(entry.getKey(), flatIngredients);
		}
		return outputIngredients;
	}
}
