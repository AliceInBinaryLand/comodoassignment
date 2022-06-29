<template>
  <v-app-bar app dense clipped-right>
    <v-app-bar-nav-icon @click="setMini"></v-app-bar-nav-icon>
    <v-spacer></v-spacer>
    <v-btn
      class="mr-2"
      @click="$vuetify.theme.dark = !$vuetify.theme.dark"
      icon
    >
      <v-icon>mdi-theme-light-dark</v-icon>
    </v-btn>
  </v-app-bar>
</template>

<script>
import { mapMutations, mapActions, mapGetters } from "vuex";
import DmNotifications from "@/components/layout/header/DmNotifications";
export default {
  components: {  DmNotifications },
  methods: {
    ...mapMutations({
      setMini: "layout/SET_MINI",
    }),
  },
};
</script>
