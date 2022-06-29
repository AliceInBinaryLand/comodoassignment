<template>
  <v-dialog
    ref="dialog"
    v-model="menu"
    :close-on-content-click="false"
    transition="scale-transition"
    min-width="auto"
    max-width="300"
  >
    <template v-slot:activator="{}">
      <ValidationProvider
        :vid="$attrs.vid"
        :name="$attrs.vName"
        :mode="$attrs.mode || 'eager'"
        :rules="rules"
        v-slot="{ errors, valid }"
      >
        <v-text-field
          v-model="innerValue"
          :error-messages="errors"
          :success="valid"
          prepend-inner-icon="mdi-calendar"
          clearable
          readonly
          v-bind="$attrs"
          @click:prepend-inner="menu = true"
        ></v-text-field>
      </ValidationProvider>
    </template>

    <v-date-picker
      v-model="innerDate"
      no-title
      scrollable
      :range="range"
      :min="min"
      :max="max"
    >
    </v-date-picker>
    <v-time-picker v-if="menu" v-model="innerTime" full-width>
      <v-spacer></v-spacer>
      <v-btn text color="primary" @click="menu = false"> Cancel </v-btn>
      <v-btn text color="primary" @click="$refs.dialog.save(innerTime)">
        OK
      </v-btn>
    </v-time-picker>
  </v-dialog>
</template>

<script>
import { ValidationProvider } from "vee-validate";
import moment from "moment";

export default {
  name: "DmDateTime",
  components: {
    ValidationProvider,
  },
  props: {
    rules: {
      type: [Object, String],
      default: "",
    },
    // must be included in props
    value: {
      type: null,
    },
    min: {
      type: null,
    },
    max: {
      type: null,
    },
    range: {
      type: Boolean,
    },
  },
  data: () => ({
    innerDate: "",
    innerTime: "",
    menu: false,
  }),
  watch: {
    // Handles internal model changes.
    innerValue(newVal) {
      this.$emit("input", newVal);
    },
    // Handles external model changes.
    value(newVal) {
      this.innerValue = newVal;
    },
  },
  computed: {
    innerValue() {
      return this.dt.fromFormat(
        this.innerDate + " " + this.innerTime,
        "dd.LL.yyyy hh:mm"
      );
    },
    formattedDate() {
      return this.innerValue
        ? this.dt.fromISO(this.innerValue).toFormat("dd.LL.yyyy")
        : "";
    },
  },
  methods: {
    deneme(on) {
      console.log(on);
    },
  },
  created() {
    console.log(this.value);
    if (this.value) {
      this.innerDate = this.value;
      this.innerTime = this.dt.fromISO(this.value).toFormat("hh:mm");
    }
  },
};
</script>
