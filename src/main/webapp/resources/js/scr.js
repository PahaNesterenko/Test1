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
        defaults: {
            id: "",
            role: {
                id: "",
                name: ""
            },
            login: "",
            password: "",
            confirmPassword: "",
            email: "",
            firstName: "",
            lastName: "",
            birthday: ""
        },
        url: "users"



    });

   App.Collections.Users = Backbone.Collection.extend({
       model: App.Models.User,
       url: "users"
   });


   App.Views.UserView = Backbone.View.extend({
       model: new App.Models.User(),
        template: _.template( $("#userLine").html() ),
       initialize: function(){
        console.log("init userView");
           this.$el = $("#main");
       },
       render : function(){
           console.log("render userLine");
           this.$el.append( this.template( this.model.toJSON() ) )
           return this;
       }
   });

    App.Views.UsersView = Backbone.View.extend({
        model : window.users,
        initialize: function(){
            console.log("init list users view");
            this.$el = $("#main");
        },
        render: function(){
            console.log("render users view");
            console.log( users );
            var self = this;
            _.each( self.model.toArray(), function(user){
                console.log("render user " + user.get("firstName"));
                self.$el.append( (new App.Views.UserView({model: user})).render().$el );
            } );
            return this;
        }
    });

    App.Views.Form = Backbone.View.extend({
        model : new App.Models.User(),
        template: _.template( $("#form").html()),
        initialize: function(){
            console.log("init form");
            this.$el = $("#view2");
        },
        render: function(){
            console.log("render form");
            var self = this;
            console.log( self.model );
            //self.$el.html( self.template( (new App.Models.User()).toJSON()).render().$el );
            self.$el.html( self.template( self.model.toJSON() ) );
            return this;
        }
    });

    var Router = Backbone.Router.extend({
       initialize: function(){
           window.users = new App.Collections.Users();
           window.view = new App.Views.UsersView({model: users});
           users.fetch().done( function(){
              view.render();
           });
       } ,
        routes: {
            "": "begin",
            "preEditUser?userId=:id": "edit",
            "go": "go",
            "deleteUser?userId=:id": "delete",
            "addUser": "add"
        },

        add: function(){
            $("#view1").hide();
            $("#view2").show();
            new App.Views.Form().render();
        },

        begin: function(){
            $("#view1").show();
            $("#view2").hide();
        },

        delete: function(id){
          console.log("delete user with id: " + id);
            window.location.href = ("");
        },

        edit: function(id1){
            console.log("edit user with id:" + id1);
            $("#view1").hide();
            $("#view2").show();
            var u = window.users.get( id1 );
            new App.Views.Form({model: u}).render();
        }

    });

       new Router();
    Backbone.history.start();

}());