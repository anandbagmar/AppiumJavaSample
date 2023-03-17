## How To Get Started
To make any of Applitools SDKs(**EyesXCUI**, **EyesEarlGrey** and **EyesImages**) work, you should install it using [CocoaPods](http://cocoapods.org), [Carthage](https://github.com/Carthage/Carthage) or manually.

## Installation with CocoaPods
### Step 1: Install gem
CocoaPods is distributed as a ruby gem, and is installed by running the following commands in terminal:
```bash
$ gem install cocoapods
```

### Step 2: Create a Podfile
Open a terminal window, and $ cd into your project directory. Then, run the following command:

```bash
$ pod init
```

### Step 3: Edit the Podfile
Specify it in your `Podfile`:
##### EyesXCUI
```ruby
target 'APPLICATION_TARGET_NAME' do
  target 'UI_TESTING_TARGET_NAME' do
    pod 'EyesXCUI'
  end
end
```
##### EyesEarlGrey
```ruby
target 'APPLICATION_TARGET_NAME' do
  target 'UNIT_TESTING_TARGET_NAME' do
    pod 'EyesEarlGrey'
  end
end
```
##### EyesEarlGrey
```ruby
target 'APPLICATION_TARGET_NAME' do
  target 'UNIT_TESTING_TARGET_NAME' do
  pod 'EyesImages'
  end
end
```

Save your `Podfile`.

### Step 4: Install dependencies
Run the following command in the terminal window:
```bash
$ pod install
```

Close Xcode, and then open your project's `.xcworkspace` file to launch Xcode.
From this time onwards, you must use the `.xcworkspace` file to open the project.

## Installation with Carthage
### Step 1: Install Carthage
You can install Carthage with [Homebrew](http://brew.sh/) using the following command:
```bash
$ brew update
$ brew install carthage
```

Or choose [another installation method](https://github.com/Carthage/Carthage#installing-carthage).

### Step 2: Create a Cartfile
Create a `Cartfile` in the same directory, where your `.xcodeproj` or `.xcworkspace` locates, using command:
```bash
$ touch Cartfile
```
### Step 3: Edit the Cartfile
Specify it in your `Cartfile`:

##### EyesXCUI
```ogdl
binary "https://applitools.bintray.com/iOS/CarthageEyesXCUI/VERSION/EyesXCUI-VERSION.json"
```

##### EyesEarlGrey
```ogdl
binary "https://applitools.bintray.com/iOS/CarthageEyesEarlGrey/VERSION/EyesEarlGrey-VERSION.json"
```

##### EyesImages
```ogdl
binary "https://applitools.bintray.com/iOS/CarthageEyesImages/VERSION/EyesImages-VERSION.json"
```

Replace 'VERSION' with the number value of SDK.
Save your `Cartfile`.

### Step 4: Install dependencies
Run the following command in the terminal window:
```bash
$ carthage update
```

### Step 5: Set up environment:
- Drag the built binary of Applitools's SDK, that you want to work with(EyesXCUI.framework, EyesEarlGrey.framework, EyesImages.framework), from Carthage/Build/<platform> into your application’s Xcode project.
- On your application targets’ Build Phases settings tab, click the + icon and choose New Run Script Phase. Create a Run Script in which you specify your shell (ex: /bin/sh), add the following contents to the script area below the shell:
```bash
/usr/local/bin/carthage copy-frameworks
```
- Click the + under `Input Files` and add an entry for each framework:
##### EyesXCUI
```bash
$(SRCROOT)/Carthage/Build/iOS/EyesXCUI.framework
```
##### EyesEarlGrey
```bash
$(SRCROOT)/Carthage/Build/iOS/EyesEarlGrey.framework
```
##### EyesImages
```bash
$(SRCROOT)/Carthage/Build/iOS/EyesImages.framework
```
- Click the + under `Output Files` and add an entry for each framework:
##### EyesXCUI
```bash
$(BUILT_PRODUCTS_DIR)/$(FRAMEWORKS_FOLDER_PATH)/EyesXCUI.framework
```
##### EyesEarlGrey
```bash
$(BUILT_PRODUCTS_DIR)/$(FRAMEWORKS_FOLDER_PATH)/EyesEarlGrey.framework
```
##### EyesImages
```bash
$(BUILT_PRODUCTS_DIR)/$(FRAMEWORKS_FOLDER_PATH)/EyesImages.framework
```

## Manual installation
1. Drag-and-drop EyesXCUI.framework to UI test target.
![](https://applitools.bintray.com/Examples/Manual%20Installation%20Guide%20Images/Step1-1.png)
![](https://applitools.bintray.com/Examples/Manual%20Installation%20Guide%20Images/Step1-2.png)

2. Open project navigator, select UI tests target(where you want to work with EyesXCUI SDK). Select **Build Phases** section.
![](https://applitools.bintray.com/Examples/Manual%20Installation%20Guide%20Images/Step2.png)

3. Select **Copy Files** phase(or create if it does not exist by tapping **+** button on the top left corner of Project Navigator).
![](https://applitools.bintray.com/Examples/Manual%20Installation%20Guide%20Images/Step3-1.png)
![](https://applitools.bintray.com/Examples/Manual%20Installation%20Guide%20Images/Step3-2.png)

4. Tap **+** button on **Copy Files** phase, find and add EyesXCUI.framework.
![](https://applitools.bintray.com/Examples/Manual%20Installation%20Guide%20Images/Step4-1.png)
![](https://applitools.bintray.com/Examples/Manual%20Installation%20Guide%20Images/Step4-2.png)

5. Change **Destination** value to *Frameworks* on **Copy Files** phase.
![](https://applitools.bintray.com/Examples/Manual%20Installation%20Guide%20Images/Step5.png)
