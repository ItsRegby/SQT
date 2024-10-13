import { test, expect } from "@playwright/test";
import { AeHomePage } from "../pages/ae-home-page";
import { AeCartPage } from "../pages/ae-cart-page";
import { AeProductsPage } from "../pages/ae-products-page";
import { AeLoginPage } from "../pages/ae-login-page";
import { AePaymentPage } from "../pages/ae-payment-page";
import { AeContactPage } from "../pages/ae-contact-page";
import { Helpers } from "../lib/helpers/helpers";
import { generateUserData } from "../lib/helpers/generateUserData";

test("User shop flow", async ({ page }) => {
  const userData = generateUserData();
  const helpers = new Helpers(page, userData);
  const aeHome = new AeHomePage(page);
  const aeProducts = new AeProductsPage(page);
  const aeCart = new AeCartPage(page, userData);
  const aeLogin = new AeLoginPage(page, userData);
  const aePayment = new AePaymentPage(page, userData);
  const aeContact = new AeContactPage(page, userData, helpers);

  await test.step("Enter the website and scroll down about halfway down the page", async () => {
    await helpers.goto();
    await aeHome.scrolldownHalfwayDownPage();
  });

  await test.step("Choose a product and click on “View product” under the picture of the product", async () => {
    await aeHome.selectProduct();
  });

  await test.step("In the Quantity box enter 30", async () => {
    await aeProducts.selectProduct_quantity(30);
  });

  await test.step("Click “Add to cart”", async () => {
    await aeProducts.addProduct_ToCart();
  });

  await test.step("Click on “Proceed to Checkout”", async () => {
    await aeCart.clickCheckoutButton();
  });

  await test.step("Click on “Register / Login”", async () => {
    await aeCart.clickCheckoutRegisterLoginButton();
  });

  await test.step("Enter name and email under “New User Signup", async () => {
    await aeLogin.enterNameAndEmail();
  });

  await test.step("Fill in all information and click on “Create Account”", async () => {
    await aeLogin.fillInForm();
    await aeLogin.clickCreateAccount();
  });

  await test.step("Click on “Continue” under “ACCOUNT CREATED!” title", async () => {
    await aeLogin.continueAfterAccountCreated();
  });

  await test.step("Click on the Cart in the header", async () => {
    await aeCart.clickCartNavBar();
  });

  await test.step("Click on “Proceed to Checkout”", async () => {
    await aeCart.clickCheckoutButton();
  });

  await test.step("Add a comment and click on “Place Order”", async () => {
    await aeCart.addComment();
    await aeCart.placeOrder();
  });

  await test.step("Fill in fake Credit Card information and click on “Pay and Confirm Order”", async () => {
    await aePayment.fillCreditCardForm();
    await aePayment.clickPayButton();
  });

  await test.step("Click on “Continue” button", async () => {
    await aePayment.clickContinueAfterPaymentButton();
  });

  await test.step("Click on “Logout” on top header", async () => {
    await helpers.clickLogoutLink();
  });

  await test.step("Click on the “Login to your account” box and enter with previously created user", async () => {
    await helpers.clickLoginLink();
    // await aeLogin.login();
    await helpers.login();
  });

  await test.step("Click on “Contact us” on the header", async () => {
    await helpers.clickContactUsLink();
  });

  await test.step("Fill required data and Click on “Submit”", async () => {
    await aeContact.fillContactUsForm();
    await aeContact.clickSubmitButton();
  });

  await test.step("Press “OK” in the pop up", async () => {
    /* The dialog is handled in the clickSubmitButton method
     ** by calling the helpers.handleDialog() method.
     ** helpers.handleDialog() needs to be declared before clicking the submit button,
     ** so the dialog can be handled.
     */
  });

  await test.step("Click on the “Logout” button on the header", async () => {
    await helpers.clickLogoutLink();
  });
});
