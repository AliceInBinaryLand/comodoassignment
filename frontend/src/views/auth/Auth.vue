<template>
  <v-row align-content="center" justify="center" align="center" class="h-100">
    <v-col cols="12" md="4" lg="4" xl="3">
<!--      <v-img-->
<!--        :src="require(`@/assets/images/logos/logo_${getTheme}.png`)"-->
<!--        contain-->
<!--        class="mb-10"-->
<!--      ></v-img>-->
      <v-dialog v-model="showRegisterModal" max-width="500px" >
        <v-card>
          <v-card-title>
            <h2 class="mt-2 align-center">Register</h2>
          </v-card-title>
          <ValidationObserver ref="userSaveForm">
            <v-form @submit.prevent="saveUser">
              <v-card-text>
                <v-card-text>
                  <dm-input
                      v-model="credentials.fullName"
                      label="Full Name"
                      rules="required"
                      prepend-inner-icon="mdi-account"
                      autocomplete="username"
                  ></dm-input>
                  <dm-input
                      v-model="credentials.email"
                      label="Email"
                      rules="required"
                      prepend-inner-icon="mdi-email-outline"
                      autocomplete="username"
                  >
                    <template></template>
                  </dm-input>
                  <dm-input
                      v-model="credentials.password"
                      :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                      label="Password"
                      rules="required"
                      :type="show ? 'text' : 'password'"
                      prepend-inner-icon="mdi-lock-outline"
                      @click:append="show = !show"
                      autocomplete="new-password"
                  >
                  </dm-input>
                </v-card-text>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="error" small text @click="close">
                  Cancel
                </v-btn>
                <v-btn color="success" small type="submit"> Save </v-btn>
              </v-card-actions>
            </v-form>
          </ValidationObserver>
        </v-card>
      </v-dialog>
      <v-card elevation="5" outlined shaped>
        <v-card-title>
          <v-row justify="center">
            <v-col class="text-center">
              <!--              <v-img-->
              <!--                height="60"-->
              <!--                src="@/assets/images/logos/loddos-logo-long-light.png"-->
              <!--                contain-->
              <!--              ></v-img>-->
              <h2 class="mt-2">Login</h2>
            </v-col>
          </v-row>
        </v-card-title>
        <ValidationObserver ref="dmForm">
          <v-form @submit.prevent="onLogin">
            <v-card-text>

              <dm-input
                v-model="credentials.email"
                label="Email"
                rules="required"
                prepend-inner-icon="mdi-email-outline"
                autocomplete="username"
              >
                <template></template>
              </dm-input>
              <dm-input
                v-model="credentials.password"
                :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                label="Password"
                rules="required"
                :type="show ? 'text' : 'password'"
                prepend-inner-icon="mdi-lock-outline"
                @click:append="show = !show"
                autocomplete="new-password"
              >
              </dm-input>
            </v-card-text>
            <v-card-actions>
              <v-btn color="primary" type="submit" block :loading="loading">
                Login
              </v-btn>
            </v-card-actions>
            <v-card-actions>
              <v-btn link color="warning" text @click="openRegisterModal()">
               Sing Up
              </v-btn>
            </v-card-actions>
          </v-form>
        </ValidationObserver>
      </v-card>
    </v-col>
  </v-row>
</template>

<!-- Load login custom page styles -->
<style lang="scss"></style>

<script>
import DmInput from "@/components/common/form/DmInput";
import ApiService from "@/services/api.service";
import { saveToken, getToken } from "@/services/jwt.service";


export default {
  name: "auth",
  components: { DmInput },
  data: () => ({
    credentials: {
      fullName: "",
      email: "",
      password: "",
    },
    show: false,
    showRegisterModal: false,
    loading: false,
  }),
  computed: {
    getTheme() {
      // if (this.$vuetify.theme.dark) {
      //   return "grey";
      // }
      // else
        return "original";
    },
  },
  methods: {
    onLogin() {
      this.$refs.dmForm.validate().then((success) => {
        if (success) {
          this.loading = true;
          let self = this;
          ApiService.post("secured/doLogin", this.credentials)
              .then(({ data }) => {
                if(data.result === true){
                  this.loading = false;
                  saveToken(data.jwt)
                  self.$router.push("/");
                }else{
                  this.$toast.setMessage("Bad Credentials", 1);
                  this.loading = false;
                }
              })
        }
      });
    },
    saveUser(){
      this.$refs.userSaveForm.validate().then( (success) =>{
        if(success){
          ApiService.post("secured/registerUser", this.credentials)
              .then(({ data }) => {
                if(data === true){
                  this.$toast.setMessage("Registration Successful. Please Login", 4);
                  this.showRegisterModal = false
                }else{
                  this.$toast.setMessage(data, 1);
                }
              })
              .catch(({ response }) => {
              });
        }
      });
    },
    openRegisterModal() {
      this.showRegisterModal = true;
    },
    close(){
      this.showRegisterModal = false;

    }
  },
};
</script>
<style>
.h-100 {
  min-height: 100vh;
}
</style>
