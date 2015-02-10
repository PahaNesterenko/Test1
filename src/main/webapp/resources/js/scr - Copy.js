(function () {

    window.App = {
        Models: {},
        Views: {},
        Collections: {},
        Routers: {}
    };

    window.template = function (id) {
        return _.template($('#' + id).html());
    };
    App.Models.User = Backbone.Model.extend({
        idAttribute: "id",
        urlRoot: 'users',
        defaults: {
            id: null,
            login: '',
            password: '',
            confirmPassword: this.password,
            firstName: '',
            lastName: '',
            email: '',
            birthday: null,
            role: { "id": null , "name":"user"},
            age: null
        },
        calculateAge: function () {
            this.set({'age': new Date().getFullYear() - new Date(this.get('birthday')).getFullYear()})
        },
        validate: function (attrs) {
            var errors = [];
            if (attrs.login.length == 0) {
                errors.push({name: 'loginError', message: 'Login is Empty'});
            }
            if (attrs.password.length == 0) {
                errors.push({name: 'passwordError', message: 'Password is Empty'})
            }
            if (attrs.password != attrs.confirmPassword) {
                errors.push({name: 'confirmPasswordError', message: 'Password are different'});
            }
            if (attrs.email.length == 0) {
                errors.push({name: 'emailError', message: 'Email is Empty'})
            }
            if (!/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(attrs.email)) {
                errors.push({name: 'emailError', message: 'Invalid email format'})
            }
            if (attrs.firstName.length == 0) {
                errors.push({name: 'firstNameError', message: 'firstName is Empty'})
            }
            if (attrs.lastName.length == 0) {
                errors.push({name: 'lastNameError', message: 'lastName is Empty'})
            }
            if (attrs.birthday.length == 0) {
                errors.push({name: 'birthdayError', message: 'Date is Empty'})
            }
            if ( !((/^([0-9]{4})-([0-9]{2})-([0-9]{2})$/.test(attrs.birthday)) || (/^([0-9]{4})-([0-9]{1})-([0-9]{1})$/.test(attrs.birthday))))  {
                errors.push({name: 'birthdayError', message: 'Invalid format of date'})
            }
            return errors.length > 0 ? errors : false;
        }
    });

    App.Collections.UsersCollection = Backbone.Collection.extend({
        model: App.Models.User,
        url: "users"
    });
    App.Views.SingleUserView = Backbone.View.extend({
        tagName: 'tr',
        template: template('usersList'),
        initialize: function () {
            this.model.on('change', this.render, this);
        },
        render: function () {
            this.model.calculateAge();
            this.$el.html(this.template(this.model.toJSON()));
            return this;
        }
    });
    App.Views.ListUsersView = Backbone.View.extend({
        el: '#usersTable',
        render: function () {
            $('#addOrEdit').hide();
            this.$el.empty();
            this.collection.each(function (user) {
                var singleUserView = new App.Views.SingleUserView({model: user});
                this.$el.append(singleUserView.render().el);
            }, this);
            return this;
        }
    });


    App.Views.addOrEditView = Backbone.View.extend({
        template: template('addOrEditScript'),
        flag: "",
        events: {
            'click #submit': 'ok'
        },
        initialize: function () {
            this.render();
        },

        render: function () {
            this.$el.html(this.template(this.model.toJSON()));
            return this;
        },
        ok: function () {
            var model = this.model;
            this.$el.find('input[name]').each(function () {
                model.set(this.name, this.value);
            });
            var role = this.$el.find('option:selected').val();
            if (role == 'admin') {
                model.set('role', { name: "admin"} );
            } else if (role == 'user') {
                model.set('role', { name: "user"} );
            }
            var errors = model.validate(model.attributes);
            if (!errors) {
                errors = [];
            }
            if (this.flag == "Add") {
                var sameLogin = view.collection.where({login: this.model.get('login')});
                if ( sameLogin.length != 0) {
                    errors.push({name: 'loginError', message: 'Login is already used'});
                }
                var sameEmail = view.collection.where({email: this.model.get('email')});
                if ( sameEmail.length != 0) {
                    errors.push({name: 'emailError', message: 'Email is already used'})
                }
            }
            if (errors.length > 0) {
                _.each(this.model.attributes, function () {
                    var err = $('.error', this.$el).empty();
                    err.removeClass(".error");
                }, this);
                _.each(errors, function (error) {
                    var err = $('#' + error.name, this.$el).empty();
                    err.addClass("error");
                    err.text(error.message);
                }, this);
            } else {
                this.model.save().done(function () {
                    window.location = '#';
                } );
            }
        }
    });

    var Router = Backbone.Router.extend({
        initialize: function () {
            window.list = new App.Collections.UsersCollection();
            window.view = new App.Views.ListUsersView({collection: list});
            view.collection.fetch({
                success: function () {
                    view.render();
                }
            });
        },
        routes: {
            "": "begin",
            "deleteUser?userId=:id": "deleteUser",
            "preAddUser": "preAddUser",
            "preEditUser?userId=:id": "preEditUser"
        },
        begin: function () {
            view.collection.fetch({
                async:false,
                success: function () {
                    view.render();
                }
            });
            $('#AllUsers').show();
        },

        deleteUser: function (id) {
            var user = view.collection.get(id);
            user.destroy({async:false});
            window.location = "#";
        },
        preAddUser: function () {
            view.collection.fetch();
            $('#AllUsers').hide();
            var addView = new App.Views.addOrEditView({model: new App.Models.User});
            addView.$el.find('#head').html('Add user');
            addView.flag = "Add";
            $('#addOrEdit').html(addView.el);
            $('#addOrEdit').show();
        },
        preEditUser: function (id) {
            view.collection.fetch();
            $('#AllUsers').hide();
            var editView = new App.Views.addOrEditView({model: list.get(id)});
            editView.$el.find('#head').html('Edit user');
            editView.$el.find('#login').prop('readonly', true);
            editView.flag = "Edit";
            $('#addOrEdit').html(editView.el);
            $('#addOrEdit').show();
        }

    });
    Backbone.history.start();
    var router = new Router();
}());