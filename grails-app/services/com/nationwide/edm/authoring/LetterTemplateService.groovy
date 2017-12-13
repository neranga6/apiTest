package com.nationwide.edm.authoring

class LetterTemplateService {

	Boolean updateIngredientSequence(String recipeParam) {
		log.debug "recipeParam: ${recipeParam}"
		Boolean success = false
		
		if(recipeParam.empty) {
			return true
		}
		
		List ingredientIds = recipeParam?.split(",")
		List<Ingredient> ingredients = ingredientIds.collect { Ingredient.get(it) }
		
		if(ingredients.contains(null)) {
			log.warn "Ingredient in recipe list ${ingredientIds} could not be found, so its sequence was not updated"
			return false
		}
		
		Ingredient.withTransaction { status ->
			ingredients.eachWithIndex() { ingredient, index ->
				try {
					ingredient.sequence = index + 1
					log.debug "updated ${ingredient.id} from sequence ${ingredient.sequence} to ${index + 1}"
					ingredient.save(flush:true)
					success = true
				} catch(RuntimeException e) {
					log.error("Problem when trying to update recipe of these ingredientIds: ${recipeParam}", e)
					success = false
				}
			}
		}
		
		return success
	}
	
	Boolean deleteIngredient(deleteIngredientId, recipe) {
		def ingredient = Ingredient.get(deleteIngredientId)
		Boolean success = false
		
		if(ingredient != null) {
			Ingredient.withTransaction { status ->
				try {
					ingredient.delete(flush: true)
					// then update the sequence of the rest of the recipe
					this.updateIngredientSequence(recipe)
					
					success = true
				} catch(RuntimeException e) {
					log.error("Problem when trying to delete ingredientId ${deleteIngredientId}", e)
				}
			}
			
		}
		
		return success
	}
	
	/**
	 * Join a LetterTemplate with a Module, which results in a new Ingredient.
	 * 
	 * @returns A map, with properties "success" that indicates whether a new Ingredient could be created, and "ingredientId".
	 */
	Map addIngredient(letterTemplateId, moduleId, user) {
		def letterTemplate = LetterTemplate.get(letterTemplateId)
		def module = Module.get(moduleId)
		Map result = [success: false]
		
		if (module && letterTemplate) {
			
			Ingredient.withTransaction { status ->
				
				try {
					def ingredient = new Ingredient(comment: "", lastModBy: user, sequence: letterTemplate.recipe.size() + 1, letter: letterTemplate, module: module)
					ingredient.save(flush: true)
					
					letterTemplate.recipe.add ingredient
					letterTemplate.save(flush: true)
					
					module.ingredients.add ingredient
					module.save(flush: true)
					
					result.ingredientId = ingredient.id
					result.success = true
				} catch(RuntimeException e) {
					log.error("Problem when trying to create an ingredient that associates letterTemplateId ${letterTemplateId} and moduleId ${moduleId}", e)
				}
				
			}
			
		} else {
			log.warn "Could not add ingredient because letterTemplateId ${letterTemplateId} or moduleId ${moduleId} does not exist."
		}
		
		log.debug "result: ${result}"
		
		return result
	}
	
}
