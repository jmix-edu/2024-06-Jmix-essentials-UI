== Readme file for theme toggle component

=== Web Component + Server-side

Vaadin client-side components are *Web Components* based on *Polymer 3*.

*Web Components* are components that are built based on a collection of web standards in the HTML specification. The standards allow you to create new HTML tags with custom names that are reusable and to fully encapsulate their functionality and styles. The standards are supported by all modern browsers.

Before we start, let's disable the Pre-Compiled Bundle, so we have live re-load for JS code.

. Add `vaadin.frontend.hot-deploy=true` to `application.properties`
. Remove `src/main/bundles/`

'''

* Create `theme-toggle.js` in the `frontend/src/component/theme-toggle` directory

* Create `ThemeToggle` class in the `com.company.timesheets.component.themetoggle` package (contact information component was placed to it's own directory as well)

* Add to existed XSD schema new element

* Implement loader

* Register `ThemeToggle` in components registry (Main application class)

* Add `xmlns:app="http://company.com/schema/app-ui-components.xsd"` to the root `mainView` element

* Add *ThemeToggle* to the `header` element on the *MainView* - so new component may be used