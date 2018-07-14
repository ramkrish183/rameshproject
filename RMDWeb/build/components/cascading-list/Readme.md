 Components Seed
===============

## Introduction

The Component Seed is a starting point for building your own Dx Component. There are several technologies that we use to make working with components easier and to aid in automating builds. We would encourage you to familiarize yourself with these tools (which we cover in the Technologies section further down the page) so your component can look and feel like everything else in the Dx ecosystem.

## Structure

It might seem like there are quite a lot of files in the seed project already. Don't worry, most of them are quite simple and are only there to provide guidance. We'll start with a high level overview and go into more detail for the items that warrant it.

- **Readme.md** — A starter readme file in the Markdown format. Stash supports Markdown and we highly encourage you to write your documentation in it.
- **History.md** — A starter history/changelog file, also in the Markdown format.
- **package.json** — An [npm](https://npmjs.org/) package file for specifying information and dependencies.
- **Gruntfile.js** — A build file for the [Grunt](http://gruntjs.com/) task runner.
- **bower.json** — A [Bower](http://bower.io/) package file for specifying information and dependencies.
- **.bowerrc** — A configuration file for Bower which tells it to look for modules on our Stash server.
- **.jshintrc** — [JSHint](http://www.jshint.com/) configuration file for managing JavaScript code quality.
- **.editorconfig** — [EditorConfig](http://editorconfig.org/) configuration file for managing coding styles within IDEs.
- **js/** — All JavaScript should go here. This folder contains an example component .js file and a [RequireJS](http://requirejs.org/) config file.
- **less/** — All styles should go here. This folder contains [LESS](http://lesscss.org/) stylesheets for basic and responsive styles. It also includes some example variables.
- **test/** — All previews of your component should go here. This folder contains a test page which demonstrates how one could demo their component.

## Requirements

A component **must** meet the following requirements to be considered part of the Design Extension ecosystem:

- Must provide a README
- Must provide an email address for support requests
- Must provide working examples in the test folder
- Must provide LESS/SASS that compiles down to regular CSS

Optionally, JavaScript tests are highly encouraged :)

## Getting Started

Following these 10 steps you should be able to generate your first component. If you are unfamiliar with any of the technology being used jump down to the Technologies section for a deeper explanation.

1. Clone the component repo. Once you've got it on your machine you'll want to delete its `.git` file so you can create your own repository.
2. Move your JavaScript files into the `js/` folder. Feel free to delete `my-component.js` or you can use it as a template to make sure your files are in the correct AMD format.
3. Add a path to your main JavaScript file in `require.config.js`. Feel free to remove the example `my-component` path.
4. Move your LESS files into the `less/` folder. Feel free to delete the example files or use them as guides.
5. If you have other components you depend on add them to the `bower.json` file.
6. If you require additional grunt tasks for you build add them to the `package.json` file and `Gruntfile.js`.
7. Run `npm install && grunt install` from the command line. This will pull down all of your dependencies, create RequireJS paths for them in `js/require.config.js` and compile all of your LESS files.
8. Configure the `index.html` inside of `test/` to render a preview of your component. You can add additional pages to test if you like.
9. Make sure that your component's `package.json` and `bower.json` files are filled out and include a name and version number for your component. Version numbers must use [Semantic Versioning.](http://semver.org/)
10. Create a repo, tag your current version and push your new component to Stash!

## Technologies

### npm

[npm](https://npmjs.org/) is a package manger for Node.js. We rely heavily on npm to install our grunt tasks, and also to install some of our tools like Bower and Grunt itself. If you've installed Node.js then npm should already be available on your system. Be sure to familiarize yourself with npm first and foremost as it is used throughout the component/Dx landscape.

### Grunt

We use [Grunt](http://gruntjs.com/) to build all of our components and Design Extensions. Take some time to familiarize yourself with the [Grunt documentation.](http://gruntjs.com/)

The Component Seed comes with two built in grunt tasks: `grunt install` and simply `grunt`. The `grunt install` task will pull down all of your dependencies using Bower and generate a RequireJS config file for you. The basic `grunt` task will just attempt to compile all of your LESS files to CSS. Under the hood `grunt install` will call `grunt` as its final step, so you should only need to run `grunt` if you've made style changes post-install.

### Bower

[Bower](http://bower.io) is a package manager for the web, primarily focused on front-end JavaScript. We use bower to install all of our component dependencies and we would strongly encourage you to do the same. By using bower, teams are able to easily add your component to their project and update it as it is improved. Bower removes the need for a lot of manual copy/paste work which can lead to errors and generally eats up time.

### RequireJS

To enforce modularity and code organization we use AMD modules and [RequireJS](http://requirejs.org/) for all of our JavaScript. We would strongly encourage you to read the [RequireJS documentation](http://requirejs.org/docs/api.html) to get familiar with how the tool works.

#### Configuring RequireJS for your project

One of the trickier aspects of working with RequireJS is knowing how to configure all of your paths and shims. To make this process easier we're using the [grunt-bower-requirejs](http://3.39.74.92:7990/projects/DT/repos/grunt-bower-requirejs/browse) task to auto-generate our config file.

Unfortunately we can't auto-generate [shim configuration options.](http://requirejs.org/docs/api.html#config-shim) In these situations there are two options:

- Fork the project and put an AMD wrapper around it. See [col-reorder-amd](http://3.39.74.92:7990/projects/DXC/repos/col-reorder-amd/browse/media/js/ColReorder.js) for an example. In your wrapper you can assume that the proper paths for dependencies will be created by the grunt-bower-requirejs task. As long as the dependencies are listed in bower.json, you should be ok.

or

- Add the shim to the top of your module, before the `define` call. For example:

``` js
require.config({
  shim: {
    'componentToShim': { exports: 'SomeComponent', deps: ['jquery'] }
  }
});

define([
  'componentToShim'
]), function() {
  ... your code ...
}
```

### LESS

We request that all components provide their styles in [LESS](http://lesscss.org/) format. This allows us to better leverage variables for color palettes, typography, etc. In this way components can have a consistent look and feel across all Design Extensions. It also ensures that they have a known API to program against and they're not inventing things which won't work in
all systems.

Take a look at [iids-navbar](http://3.39.74.92:7990/projects/DXC/repos/iids-navbar/browse) to see how you can structure your LESS dependencies. When you feel like you've reached a stopping point take a look at the Gruntfile to make sure all of your LESS files will be compiled to CSS.

``` js
less: {
  compile: {
    options: {
      paths: ['less/', 'components/']
    },
    files: [{
      expand: true,
      cwd: 'less/',
      src: ['*.less', '!variables.less'],
      dest: 'css/',
      ext: '.css',
      nonull: true
    }]
  }
},
```
By default the task will attempt to compile all LESS files in the `less/` folder to individual stylesheets. It is setup to exclude `variables.less`. If you'd like to exclude additional files add them to the `src` Array.

### Brandkit

[Brandkit](http://3.39.74.92:7990/projects/DXC/repos/brandkit/browse)
Brandkit is a convenience library for normalizing GE brand standards across projects that use LESS/CSS and JavaScript. Take a moment to read through the [Brandkit documentation](http://3.39.74.92:7990/projects/DXC/repos/brandkit/browse) to familiarize yourself with this tool. In general, if you want to style something so that any Dx can change its appearnce, you'll want to style it with variables from Brandkit.

#### When to use it

- If you need **variables** specific to GE Design Extensions (colors, type, etc) or Twitter Boostrap, check in [Brandkit.](http://3.39.74.92:7990/projects/DXC/repos/brandkit/browse)
- If you need **mixins** specific to GE Design Extensions (voice, color, logo), check in [Brandkit.](http://3.39.74.92:7990/projects/DXC/repos/brandkit/browse)
- If you need **mixins** specific to Twitter Bootstrap, check in [GE Bootstrap.](http://3.39.74.92:7990/projects/DXC/repos/ge-bootstrap/browse)

#### Why are there Bootstrap variables in Brandkit?

Bootstrap has a lot of variables for things like color palettes and typography but not every component should have to rely on Bootstrap just to know what colors to use. We extracted the variables from Bootstrap and added some of our own so that Brandkit can serve as a general purpose API for any Dx component. This does create some additional cognitive overhead so please let us know if this workflow doesn't feel right to you. We're always open to revising the structure to improve developer happiness.

### RECOMMENDED READING

[SMACSS](http://smacss.com/) - Scalable and Modular Architecture for CSS

[Javascript the Good Parts](http://shop.oreilly.com/product/9780596517748.do) - Insights into Javascript by it's creator Douglas Crockford

---

## FAQ

### What does the tilde "~" mean in the version numbers?

The tilde is used to specify a version range. `~1.9.1` translates to "any version greater than or equal to 1.9.1 but less than 1.10.0".

Bower uses [Semantic Versioning (aka semver)](http://semver.org/) just like npm. [Here's a great explanation of all the ways you can specify version ranges in semver.](https://npmjs.org/doc/json.html#dependencies) Any component in the Design Extension ecosystem **must** use semantic versioning, otherwise tools like bower will be unable to consume it.


### Why do some dependencies have dxc in front of their version number?

Typically you install bower components from [the bower registry](http://sindresorhus.com/bower-components/), which is a publicly hosted service. At present GE does not have its own registry so we use a `shorthand_resolver` attribute in our `.bowerrc` files to tell bower to look in our Stash repos, instead of Github, for components.

``` js
// In our component's .bowerrc
// 3.39.74.92:7990 is the path to our Stash instance
{
  "shorthand_resolver": "http://3.39.74.92:7990/scm/{{{ organization }}}/{{{ package }}}.git"
}
```

You can read more [about .boewrrc configuration here.](https://github.com/bower/bower?source=c#configuration) In this case `dxc` is the project name for our collection of components. For example, the charts component is located at: http://3.39.74.92:7990/scm/**dxc/charts.git**. Using the `shorthand_resolver` we can just tell bower to look for that component at `dxc/charts`.

*Note: You do not need to include the trailing `.git` extension in your bower.json paths if you're using the `shorthand_resolver`. Doing so will cause bower to throw an error.*

### I made changes to a component but bower keeps pulling down the old code

There are two things that can be going wrong in this situation.

1. You made code changes but you didn't create a new tag
2. You replaced an existing tag but didn't clear your bower cache

#### You made code changes but you didn't create a new tag

Bower works off of tags, so if you add code to your component you have to version up the tag. This is why [semver](http://semver.org/) is so important. You want all those version numbers to mean something. If you haven't created a new tag **and pushed it to the remote** then you won't see any changes.

To create a local tag:
``` bash
$ git tag v0.1.0
```

To push your local tags to your remote:
``` bash
$ git push origin --tags
```

To delete a local tag:
``` bash
$ git tag -d v0.1.0
```

To delete a tag on your remote:
``` bash
# WARNING! Make sure no one is using the tag!
$ git push origin :/refs/tags/v0.1.0
```

#### You replaced an existing tag but didn't clear your bower cache

Perhaps you tagged something that was broken and needed to delete the tag locally and on your remote using the steps outlined above. Even after you've done this bower is still holding a cached instance of your library at the previous, bad tag. To tell bower to clear its cache use:

``` bash
# Will clear everything in bower's cache
$ bower cache-clean

# Will just clear the cache of your component
$ bower cache-clean <component>
```
After you've cleaned the cache it might also be a good idea to delete that component's folder from the `components` directory. Then you can try to run `grunt install` again.

### grunt install freezes

The `grunt install` command can take a long time to run, especially if you've recently cleaned your bower cache with `bower cache-clean`. If it seems to be taking *way* too long you can interrupt the process with `ctrl-c`. Then just trying running `grunt install` again. Often times this will unstick whatever was hanging.


### Fatal error: status code of git: 128

This can occur during a `grunt install` and the error is not exactly helpful. Instead of running `grunt install` try deleting your `components` folder and then run `bower install`. Often times you'll get better debugging output if you run the underlying bower task instead of its grunt equivalent.

#### Missing shorthand_resolver

If you just run `bower install` and see that it is trying to pull down your component from `https://github.com/dxc/...` then you're missing the `shorthand_resolver` in your `.bowerrc` file. By default bower will search for components on Github, the `shorthand_resolver` tells it to look at Stash instead. To fix this just add the following to your `.bowerrc` file in the root of your component project.

``` json
{
  "shorthand_resolver": "http://3.39.74.92:7990/scm/{{{ organization }}}/{{{ package }}}.git"
}
```
Paste that in exactly as it is written, making sure to leave the mustache template tags untouched.

#### Unnecessary .git extension

When using the `shorthand_resolver` you should exclude the `.git` extension on the end of your paths in `bower.json`.

``` json
// Good
"ge-bootstrap": "dxc/ge-bootstrap#~0.4.4",

// Bad
"ge-bootstrap": "dxc/ge-bootstrap.git#~0.4.4",
```

### There are old paths in my require.config.js file

When the `grunt-bower-requirejs` task runs it does not replace your `require.config.js` file. It overwrites paths that are already in `bower.json` and it ammends new ones. If you change a component's name in `bower.json` or if you remove a component, then the old path will remain. You'll need to go into the `require.config.js` file and manually delete this old path.


### grunt install warns me that bootstrap wants an older version of jquery

``` bash
Please note that
    ge-bootstrap requires jquery ~1.9.1
    bootstrap requires jquery ~1.8.0

Resolved to jquery v1.9.1, which matches the requirement defined in the project's bower.json.
Conflicts may occur.
```

This shouldn't be a problem. There is no way to change which version of jquery bootstrap requests. In our testing we have not noticed any issues with using 1.9.1.


