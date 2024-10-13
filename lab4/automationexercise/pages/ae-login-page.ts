import { expect, type Locator, type Page } from "@playwright/test";
import { UserData } from "../lib/helpers/generateUserData";

export class AeLoginPage {
  readonly page: Page;
  readonly userData: UserData;

  readonly signupName: Locator;
  readonly signupEmail: Locator;
  readonly emailExistsError: Locator;
  readonly signupButton: Locator;

  readonly formName: Locator;
  readonly formEmail: Locator;
  readonly formPassword: Locator;
  readonly formBirthDay: Locator;
  readonly formBirthMonth: Locator;
  readonly formBirthYear: Locator;
  readonly formFirstName: Locator;
  readonly formLastName: Locator;
  readonly formCompany: Locator;
  readonly formAddress: Locator;
  readonly formCountry: Locator;
  readonly formState: Locator;
  readonly formCity: Locator;
  readonly formZipCode: Locator;
  readonly formMobileNumber: Locator;

  readonly createAccountButton: Locator;

  readonly continueButton: Locator;

  constructor(page: Page, userData: UserData) {
    this.page = page;
    this.userData = userData;

    this.signupName = page.getByPlaceholder("Name");
    this.signupEmail = page
      .locator("form")
      .filter({ hasText: "Signup" })
      .getByPlaceholder("Email Address");
    this.signupButton = page.getByRole("button", { name: "Signup" });
    this.emailExistsError = page.getByText("Email already exists");
    this.formName = page.locator('[data-qa="name"]');
    this.formEmail = page.locator('[data-qa="email"]');

    this.formPassword = page.locator('[data-qa="password"]');
    this.formBirthDay = page.locator('[data-qa="days"]');
    this.formBirthMonth = page.locator('[data-qa="months"]');
    this.formBirthYear = page.locator('[data-qa="years"]');
    this.formFirstName = page.locator('[data-qa="first_name"]');
    this.formLastName = page.locator('[data-qa="last_name"]');
    this.formCompany = page.locator('[data-qa="company"]');
    this.formAddress = page.locator('[data-qa="address"]');
    this.formCountry = page.locator('[data-qa="country"]');
    this.formState = page.locator('[data-qa="state"]');
    this.formCity = page.locator('[data-qa="city"]');
    this.formZipCode = page.locator('[data-qa="zipcode"]');
    this.formMobileNumber = page.locator('[data-qa="mobile_number"]');

    this.createAccountButton = page.locator('[data-qa="create-account"]');

    this.continueButton = page.locator('[data-qa="continue-button"]');
  }

  async enterNameAndEmail() {
    await this.signupName.fill(this.userData.fullName);
    await this.signupEmail.fill(this.userData.email);
    await this.signupButton.click();
    await this.emailExists();
    await expect(this.formName).toHaveValue(this.userData.fullName);
    await expect(this.formEmail).toHaveValue(this.userData.email);
  }

  async fillInForm() {
    await this.formPassword.fill(this.userData.password);
    await this.formBirthDay.selectOption({
      value: this.userData.day.toString(),
    });
    await this.formBirthMonth.selectOption({ value: this.userData.month });
    await this.formBirthYear.selectOption({
      value: this.userData.year.toString(),
    });
    await this.formFirstName.fill(this.userData.firstName);
    await this.formLastName.fill(this.userData.lastName);
    await this.formCompany.fill(this.userData.company);
    await this.formAddress.fill(this.userData.address);
    await this.formCountry.selectOption({ index: this.userData.country });
    await this.formState.fill(this.userData.state);
    await this.formCity.fill(this.userData.city);
    await this.formZipCode.fill(this.userData.zipCode);
    await this.formMobileNumber.fill(this.userData.mobileNumber);
  }

  async clickCreateAccount() {
    await this.createAccountButton.click();
  }

  async emailExists() {
    if (await this.emailExistsError.isVisible()) {
      console.log("Email already exists. Generating new email.");
      return this.enterNameAndEmail();
    }
  }

  async continueAfterAccountCreated() {
    await this.continueButton.click();
  }
}
