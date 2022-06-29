<template>
  <v-snackbar
    v-model="show"
    :top="type === 2"
    :color="handleColor"
    :timeout="type === 2 ? 1500 : 2500"
    min-width="50px"
    transition="scale-transition"
  >
    <div class="d-flex justify-space-between align-center">
      <v-icon class="mr-2">{{
        type === 2 ? "mdi-check-circle-outline" : "mdi-alert"
      }}</v-icon>
      <h4>{{ message }}</h4>
    </div>
  </v-snackbar>
</template>

<script>
export default {
  name: "DmToast",
  data() {
    return {
      show: false,
      message: "",
      type: "",
    };
  },
  computed: {
    handleColor() {
      if (this.type === 1) {
        return "error";
      } else return "success";
    },
  },

  created() {
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "SET_MESSAGE") {
        this.message = state.message.content;
        this.type = state.message.type;
        this.show = true;
      }
    });
  },
};
</script>
<style scoped>
::v-deep .v-snack__wrapper {
  min-width: 100px;
}
</style>
