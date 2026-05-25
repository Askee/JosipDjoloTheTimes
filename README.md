# Josip Djolo - The Times Task

# The task - StackOverflow Users

Develop an Android application that fetches a list of StackOverflow users and displays it in a
list on the screen.

## Functional Requirements

* When the app is launched, the user should be able to see a list of the top 20
StackOverflow users.
*  Each list item should contain the user's profile image, name and reputation.
*  Cells should contain an additional option to 'follow' a user. “Follow” functionality
should just be locally simulated, i.e. no actual API call should be made.
    *   Users that are followed should show an indicator in the list item.
    * Include an 'unfollow' option in the view when a user is followed.
    *  “Follow” status should persist between sessions.
* If the server is unavailable (e.g. offline, error response etc), the user should see an
empty state with an error message.

## Technical Specifications
* Emphasise testability and architecture - use whatever pattern you like, but be
prepared to justify your decision!
* Your code should be covered by unit tests.
* Your UI is entirely up to you, but please use Compose to build your views.
* Write in Kotlin - no Java.
* No 3rd party UI frameworks (apart from the exceptions below) - we want to see what
you can do!
    * Coil
    * Retrofit
    * Dagger/Hilt
* **Please avoid using AI to write this project**
* Deliver your work as a Git repository, preferably on Github or Bitbucket so we can
see your commit history.
* Include a README explaining how your app works, any installation requirements,
and details behind any technical decisions you made while developing it.

## Additional Information

This is a take-home exercise and you should take as much time as you need, but try not to
spend too long on it. All code must be your own, please avoid using AI to generate any code
for you.

# Implementation notes

## Additional features

This implementation has, in addition to the task itself, also introduced:
* Dark/Light theming
* Multipreviews and a robust implementation of snapshot tests (Paparazzi) for various font scalings and the two themes
* Component catalog (Showkase), leveraged for the snapshot tests
* Skeleton UI for loading
* Persistance achieved in two ways: DataStore and Room, one of which is surplus to requirements but a good demonstration of clean architecture's flexibility

## Missing features

This implementation has, in interest of time, not covered everything with tests. Author has tried to demonstrate various kinds of tests, but not spend too much time covering everything as it's often trivial to just expand on what's already implemented. Notably what is missing as an aspect of testing are mapper tests and ViewModel tests.

Additionaly, the UI satisfies the requirements but colour palette could've been prettier and more focused on contrast and similar issues.

Lastly, there are some minor TODOs left in the code, not concerned with functionality but rather code-style and similar.

# Snapshot tests in general

In general, Snapshot Tests allow us to detect any changes in UI components which are utilising the `@JosipPreviews` annotation, and to either accept those changes if intended, or to fix the root cause if unintended.

Snapshot Tests on Android are done by leveraging [Airbnb's Showkase](https://github.com/airbnb/Showkase) library which lets us discover all the Compose Previews, enabling us to then feed them into the Snapshot tests created via [CashApp's Paparazzi](https://github.com/cashapp/paparazzi) library. Those tests are parametrized using [Google's TestParameterInjector](https://github.com/google/TestParameterInjector).

## The building blocks

As mentioned, I've introduced three libraries specifically for purposes of Snapshot Testing on Android:
* [Showkase by Airbnb](https://github.com/airbnb/Showkase) - annotation-processor based library that helps organize, discover, search and visualize Jetpack Compose UI elements
* [Paparazzi by CashApp](https://github.com/cashapp/paparazzi) - a library to render application screens without a physical device or emulator, and generate snapshot tests
* [TestParameterInjector by Google](https://github.com/google/TestParameterInjector) - A JUnit4 and JUnit5 parameterized test runner

 ## The concept

 The way Snapshot Tests work is:
* We've created the initial snapshots, i.e. golden values, recorded as images and hosted on Git
* Upon doing any changes, we can now verify if anything changed compared to the golden values
* In case of any changes, we need to inspect the generated diff files, and either accept the changes if they're intended by re-recording the new state as golden values, or resolve the issues by fixing the cause if unintended

## Usage

To record golden values, run. the following gradle command:

`./gradlew :app:verifyPaparazziDebug`

Images are recorded in `./app/src/test/snapshots/images`.

To verify, run

`./gradlew :app:recordPaparazziDebug`

After running record or verify, the Paparazzi report is recorded in `app/build/reports/paparazzi/debug/index.html`
