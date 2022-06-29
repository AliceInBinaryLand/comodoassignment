import Vue from "vue";
import { ValidationObserver, ValidationProvider } from "vee-validate";
import {
  required,
  email,
  min,
  max,
  regex,
  numeric,
  alpha_num,
  length,
  integer,
  confirmed,
} from "vee-validate/dist/rules";
import { extend } from "vee-validate";

extend("required", {
  ...required,
  message: "This field is required",
});

extend("min", {
  ...min,
  message: "This field must be {length} characters or more",
});

extend("max", {
  ...max,
  message: "This field must be {length} characters or less",
});

extend("email", {
  ...email,
  message: "This field must be a valid email",
});
extend("numeric", {
  ...numeric,
  message: "The field may only contain numeric characters",
});
extend("integer", {
  ...integer,
  message: "The field may only contain integers",
});
extend("alpha_num", {
  ...alpha_num,
  message: "The field may only contain numbers and letters",
});
extend("length", length);

extend("regex", regex);

extend("confirmed", {
  ...confirmed,
  message: "Passwords do not match",
});

extend("sameDomain", {
  params: ["target"],
  validate(value, { target }) {
    let domain = value.split("@")[1];
    if (Array.isArray(target)) {
      return !!target.find((v) => v === domain);
    } else return domain === target;
  },
  message: "Users email addresses must be from contractor domain",
});

Vue.component("ValidationObserver", ValidationObserver);
Vue.component("ValidationProvider", ValidationProvider);
