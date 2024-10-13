import { expect, type Locator, type Page } from "@playwright/test";
import { UserData, generateUserData } from "../lib/helpers/generateUserData";

export class AePaymentPage {
  readonly page: Page;
  userData: UserData;

  readonly fieldCardName: Locator;
  readonly fieldCardNumber: Locator;
  readonly fieldCardCVC: Locator;
  readonly fieldCardExpirationMonth: Locator;
  readonly fieldCardExpirationYear: Locator;
  readonly payButton: Locator;

  constructor(page: Page, userData: UserData) {
    this.page = page;
    this.userData = userData;

    this.fieldCardName = page.locator('[data-qa="name-on-card"]');
    this.fieldCardNumber = page.locator('[data-qa="card-number"]');
    this.fieldCardCVC = page.locator('[data-qa="cvc"]');
    this.fieldCardExpirationMonth = page.locator('[data-qa="expiry-month"]');
    this.fieldCardExpirationYear = page.locator('[data-qa="expiry-year"]');
    this.payButton = page.locator('[data-qa="pay-button"]');
  }

  async fillCreditCardForm() {
    await this.fieldCardName.fill(this.userData.fullName);
    await this.fieldCardNumber.fill(this.userData.creditCardNumber);
    await this.fieldCardCVC.fill(this.userData.creditCardCVC);
    await this.fieldCardExpirationMonth.fill(
      this.userData.creditCardExpirationMonth
    );
    await this.fieldCardExpirationYear.fill(
      this.userData.creditCardExpirationYear
    );
  }

  async clickPayButton() {
    await this.payButton.click();
  }

  async clickContinueAfterPaymentButton() {
    await this.page.click('[data-qa="continue-button"]');
  }
}
