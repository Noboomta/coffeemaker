/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 *
 * Permission has been explicitly granted to the University of Minnesota
 * Software Engineering Center to use and distribute this source for
 * educational purposes, including delivering online education through
 * Coursera or other entities.
 *
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including
 * fitness for purpose.
 *
 *
 * Modifications
 * 20171114 - Ian De Silva - Updated to comply with JUnit 4 and to adhere to
 * 							 coding standards.  Added test documentation.
 */
package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

/**
 * Unit tests for CoffeeMaker class.
 *
 * @author Sarah Heckman
 */
public class CoffeeMakerTest {

	/**
	 * The object under test.
	 */
	private CoffeeMaker coffeeMaker;

	// Sample recipes to use in testing.
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;

	/**
	 * Initializes some recipes to test with and the {@link CoffeeMaker}
	 * object we wish to test.
	 *
	 * @throws RecipeException  if there was an error parsing the ingredient
	 * 		amount when setting up the recipe.
	 */
	@Before
	public void setUp() throws RecipeException {
		coffeeMaker = new CoffeeMaker();

		//Set up for r1.
		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0");
		recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");
		recipe1.setAmtSugar("1");
		recipe1.setPrice("50");

		//Set up for r2
		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("20");
		recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");
		recipe2.setAmtSugar("1");
		recipe2.setPrice("75");

		//Set up for r3
		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0");
		recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");
		recipe3.setAmtSugar("1");
		recipe3.setPrice("100");

		//Set up for r4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with well-formed quantities
	 * Then we do not get an exception trying to read the inventory quantities.
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddInventory() throws InventoryException {
		coffeeMaker.addInventory("4","7","0","9");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantities (i.e., a negative
	 * quantity and a non-numeric string)
	 * Then we get an inventory exception
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryException() throws InventoryException {
		coffeeMaker.addInventory("4", "-1", "str", "3");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with the formal quantities
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddNormalInventory() throws InventoryException {
		coffeeMaker.addInventory("2", "3", "4", "5");
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with formal quantities
	 * The string from checkInventory() has to be the same as expected.
	 *
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testCheckInventory() throws InventoryException {
		coffeeMaker.addInventory("0","1","2","3");
		String expectedInventory = "Coffee: 15\nMilk: 16\nSugar: 17\nChocolate: 18\n";
		assertEquals(expectedInventory, coffeeMaker.checkInventory());
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add recipe to recipe book and call the
	 * makeCoffee with the enough money the chance should be correct.
	 */
	@Test
	public void testCheckChance() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(5, coffeeMaker.makeCoffee(0, 55));
	}

	/**
	 * Given a coffee maker with the default inventory
	 * But we Don't add recipe to recipe book so after call the
	 * makeCoffee with the money the chance should not be changed.
	 */
	@Test
	public void testCheckChanceOfNullRecipe() {
		assertEquals(50, coffeeMaker.makeCoffee(0, 50));
	}

	/**
	 * Given a coffee maker with the default inventory
	 * When we add recipe to recipe book and call the
	 * makeCoffee with the enough money the inventory
	 * should be decreased as it should be.
	 */
	@Test
	public void testInventoryReducedCorrectly() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.makeCoffee(0, 50);
		String expectedInventory = "Coffee: 12\nMilk: 14\nSugar: 14\nChocolate: 15\n";
		assertEquals(expectedInventory, coffeeMaker.checkInventory());
	}

	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying more than
	 * 	the coffee costs
	 * Then we get the correct change back.
	 */
	@Test
	public void testMakeCoffee() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(25, coffeeMaker.makeCoffee(0, 75));
	}

	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying more than
	 * the coffee costs
	 * Then we get change that equal to the input back.
	 */
	@Test
	public void testMoneyNotEnough() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals(25, coffeeMaker.makeCoffee(0, 25));
	}

	/**
	 * Given a coffee maker with 4 valid recipe
	 * The coffee maker should not able to add it, since they can add just 3.
	 */
	@Test
	public void testOverRecipes() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.addRecipe(recipe3);
		coffeeMaker.addRecipe(recipe4);
		assertEquals(3, coffeeMaker.getRecipes().length);
	}

	/**
	 * Given a coffee maker with 4 valid recipe
	 * The coffee maker should not able to add it, since they can add just 3.
	 */
	@Test
	public void testAddExceedAmountOfRecipe(){
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe2);
		coffeeMaker.addRecipe(recipe3);
		assertFalse(coffeeMaker.addRecipe(recipe4));
	}

	/**
	 * Given a coffee maker 2 of same recipe it should not able to make it.
	 */
	@Test
	public void testAddDuplicateRecipes() {
		coffeeMaker.addRecipe(recipe1);
		coffeeMaker.addRecipe(recipe1);
		assertEquals(null, coffeeMaker.getRecipes()[1]);
	}

	/**
	 * Given a coffee maker 2 of same recipe it should not able to make it.
	 */
	@Test
	public void testAddDuplicateRecipes2() {
		coffeeMaker.addRecipe(recipe1);
		assertFalse(coffeeMaker.addRecipe(recipe1));
	}

	/**
	 * Given a coffee maker add recipe that is not valid, so it should throw the RecipeException.
	 */
	@Test(expected = RecipeException.class)
	public void testInValidRecipesException() throws RecipeException {
		recipe4 = new Recipe();
		recipe4.setName("InValidCoffee");
		recipe4.setAmtChocolate("-1");
		recipe4.setAmtCoffee("-2");
		recipe4.setAmtMilk("-2");
		recipe4.setAmtSugar("-2");
		recipe4.setPrice("-5");
	}

	/**
	 * Given a coffee maker 1 recipe it should able to delete it.
	 */
	@Test
	public void testDeleteRecipe() {
		coffeeMaker.addRecipe(recipe1);
		assertEquals("Coffee", coffeeMaker.deleteRecipe(0));
	}

	/**
	 * Given a coffee maker non recipe, it should return null if we delete recipe.
	 */
	@Test
	public void testDeleteNullRecipe() {
		assertEquals(null, coffeeMaker.deleteRecipe(0));
	}

	/**
	 * Given a coffee maker 1 recipe, it should return recipe name when we edit or getRecipe of recipe.
	 */
	@Test
	public void testEditRecipe(){
		coffeeMaker.addRecipe(recipe1);
		assertEquals("Coffee", coffeeMaker.editRecipe(0, recipe1));
		assertEquals("Coffee" , coffeeMaker.getRecipes()[0].getName());
	}

	/**
	 * Given a coffee maker non recipe, it should return null if we edit recipe.
	 */
	@Test
	public void testEditNoRecipe(){
		assertEquals(null, coffeeMaker.editRecipe(0, recipe1));
	}

	/**
	 * Add valid Inventory in part of valid sugar.
	 * @throws InventoryException
	 */
	@Test
	public void testAddSugar1() throws InventoryException{
		coffeeMaker.addInventory("0","0","1","0");
	}

	/**
	 * Add valid Inventory in part of invalid sugar. It will be expected that raise InventoryException.
	 * @throws InventoryException
	 */
	@Test(expected = InventoryException.class)
	public void testAddSugar2() throws InventoryException{
		coffeeMaker.addInventory("0","0","-1","0");
	}

	/**
	 * Add valid Inventory in part of valid coffee.
	 * @throws InventoryException
	 */
	@Test
	public void testAddCoffee1() throws InventoryException{
		coffeeMaker.addInventory("1","0","0","0");
	}

	/**
	 * Add valid Inventory in part of invalid coffee. It will be expected that raise InventoryException.
	 * @throws InventoryException
	 */
	@Test(expected = InventoryException.class)
	public void testAddCoffee2() throws InventoryException{
		coffeeMaker.addInventory("-1","0","0","0");
	}

	/**
	 * Add valid Inventory in part of valid milk.
	 * @throws InventoryException
	 */
	@Test
	public void testAddMilk1() throws InventoryException{
		coffeeMaker.addInventory("0","1","0","0");
	}

	/**
	 * Add valid Inventory in part of invalid milk. It will be expected that raise InventoryException.
	 * @throws InventoryException
	 */
	@Test(expected = InventoryException.class)
	public void testAddMilk2() throws InventoryException{
		coffeeMaker.addInventory("0","-1","0","0");
	}

	/**
	 * Add valid Inventory in part of valid chocolate.
	 * @throws InventoryException
	 */
	@Test
	public void testAddChocolate1() throws InventoryException{
		coffeeMaker.addInventory("0","0","0","1");
	}

	/**
	 * Add valid Inventory in part of invalid chocolate. It will be expected that raise InventoryException.
	 * @throws InventoryException
	 */
	@Test(expected = InventoryException.class)
	public void testAddChocolate2() throws InventoryException{
		coffeeMaker.addInventory("0","0","0","-1");
	}

	/**
	 * Add valid Inventory in part of valid value but all being 0.It should be able to do, no exception class raised.
	 * @throws InventoryException
	 */
	@Test()
	public void testAddAllZero() throws InventoryException{
		coffeeMaker.addInventory("0","0","0","0");
	}
}
