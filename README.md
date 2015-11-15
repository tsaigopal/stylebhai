# stylebhai
framework for developing desktop apps with swt and spring

#Features
* SWT/JFace based GUI implementation
* XML/HTML/Properties based screen design
* Annotation based event listeners
* Autowire spring beans (services/DAO)
* Maven projects

#Setup
* Checkout framework and sample code
* Import as maven project into your favorite IDE and get going

#QuickStart
Lets build a basic application with stylebhai.
* First create a maven project and add the dependency to stylebhia. (Its not yet added maven central, check this out as a project into the same workspace.)

* Next, you should have a model class for the screen, a simple value object should do. Example: a Customer
```
	public class Customer {
		private int custId;
		private String custName;
		private String address;
		//////////
		getters and setters
		//////////
	}
```
* Create the screen class, by extending AbstractBindableScreen<T>. Lets call it CustomerDetailScreen.
Obviously we have to provide the default constructor and implement the abstract method initialize (we will talk the arguments later).
so he is what it would look like
```
	
	package com.wacaw.stylebhai.example;
	
	public class CustomerDetailScreen extends AbstractBindableScreen<Customer> {
		public CustomerDetailScreen() {
			super("customer", null);  //specify title and icon name. won't worry about icon now, so just pass null.
		}
		public void initialize(Object...objects) throws Exception {
			//lets create a customer which we will show to the users and set it as model.
			Customer c = new Customer();
			c.setCustId(1);
			c.setCustName("my name");
			setModel(c); 
		}
	}
```
* Lets add the screen layout now. Stylebhai supports properties, xml, html as screen configuration. We will use the simplest one i.e. html. Remember the name of the layout file has to be same as the screen class (case sensitive) and
to be kept in the same package. So, we will create a file called CustomerDetailScreen.html and keep it with the class.
```
  <table>
    <tr>
      <td>Customer Id</td>
      <td id="custId"></td>   <!-- its a place holder for displaying the id field -->
    </tr>
    <tr>
      <td>Customer Name</td>
      <!-- we need a textbox here to display the name. Be careful with the value of id. 
      It should match the property name of Customer -->
      <td><input type="text" id="custName"/></td> 
    </tr>
  </table>    
```

* Ok, we are all set to run it now. So create a Main class and call StylerApp.run() to run the window.

```
public class OneMain {
	public static void main(String[] args) {
		StylerApp.run(new UILauncher() {  //need to provide a UILauncher instance to create spring container and all internals.
			public void startUI(WidgetBuilder builder) {
				builder.createWindow(CustomerDetailScreen.class); //call builder methods to create your screen.
			}
		});
	}
}

```

#Framework Components
The framework is built using SWT and Spring, apache commons (all open source)
* WidgetConfigFactory: Used for creating screen configuration hierarchy. Implementation are available for properties, xml and html.
* WidgetBuilder: Core abstract class, used for creating screens. Currently only SWT implementation is available. The package also contains some implementation agnostic classes and interfaces which can be used. It can be auto wired into screen classes or other components if required.
* Screen classes: Defines behaviour of a screen, always have to associated with a layout file (xml/html/..), need to extend AsbtractScreen or AbstractBindableScreen. Must have a no-arg constructor. This doesn't have to be spring bean, but it can have dependencies which will be autowired by the framework.
* Event Handling: Methods in screen class can be annotated with @EventListner for adding listeners to the widgets. Additionally WidgetBuilder provides methods to add custom implementation.
* Datbase support: The application creates an entity manager and transaction manager if it finds an persistence.xml inside META-INF folder. You can just inject the EntityManager in you DAOs.
* Windows: There can be 3 type of screens in the application.
	** MDIWindow: Used as main window for the application, it will launch child windows with in itself. Supports menu, quick links, toolbar, status bar.
	** Window: Used to contain a screen and its widgets. You can get a handle to it through getWindowHandle() in screen class.
	** Dialogs: Just like Window, but opened as a child window.
* Widgets: Screens can define widgets as properties to work with them (set value, text, visibility). In most case it won't be required as AbstractBindableScreen takes care of 2 way binding. If defined they will be injected by the framework before initialize() is called.

Read java docs of classes for more details.