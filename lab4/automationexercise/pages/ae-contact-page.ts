import { expect, type Locator, type Page } from "@playwright/test";
import { UserData } from "../lib/helpers/generateUserData";
import { Helpers } from "../lib/helpers/helpers";

export class AeContactPage {
  readonly page: Page;
  readonly userData: UserData;
  readonly helpers: Helpers;

  readonly fieldName: Locator;
  readonly fieldEmail: Locator;
  readonly fieldSubject: Locator;
  readonly fieldMessage: Locator;
  readonly submitButton: Locator;

  readonly formSuccessMessage: Locator;

  constructor(page: Page, userData: UserData, helpers: Helpers) {
    this.page = page;
    this.userData = userData;
    this.helpers = helpers;

    this.fieldName = page.locator('[data-qa="name"]');
    this.fieldEmail = page.locator('[data-qa="email"]');
    this.fieldSubject = page.locator('[data-qa="subject"]');
    this.fieldMessage = page.locator('[data-qa="message"]');
    this.submitButton = page.locator('[data-qa="submit-button"]');

    this.formSuccessMessage = page
      .locator("#contact-page")
      .getByText("Success! Your details have been submitted successfully.");
  }

  async fillContactUsForm() {
    await this.fieldName.fill(this.userData.fullName);
    await this.fieldEmail.fill(this.userData.email);
    await this.fieldSubject.fill(this.userData.contactSubject);
    await this.fieldMessage.fill(this.userData.contactMessage);
  }

  async clickSubmitButton() {
    await this.helpers.handleDialog();
    await this.submitButton.click();
    await this.verifyContactUsFormSubmission();
  }

  async verifyContactUsFormSubmission() {
    await expect(this.formSuccessMessage).toBeVisible();
  }
}
