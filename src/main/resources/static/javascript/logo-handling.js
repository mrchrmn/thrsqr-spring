/* eslint-disable max-statements */

/* eslint-disable max-lines-per-function */
function getS3Request(file, eventCode) {
  let xhr = new XMLHttpRequest();

  xhr.open("GET", `/s3request?fileType=${file.type}&eventCode=${eventCode}`);

  xhr.addEventListener("readystatechange", () => {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        let response = JSON.parse(xhr.responseText);
        uploadFile(file, response.s3request, response.url);

      } else {
        console.log("Could not get S3 URL");
      }
    }
  });

  xhr.send();
}


function uploadFile(file, s3request, url) {
  let xhr = new XMLHttpRequest();

  xhr.open("PUT", s3request);

  xhr.addEventListener("readystatechange", () => {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        document.getElementById("eventLogo").setAttribute("src", url + `?${Math.random()}`);
        document.getElementById("eventLogoURL").setAttribute("value", url);

      } else {
        console.log("Could not upload file.");
      }
    }
  });

  xhr.send(file);
}


document.addEventListener("DOMContentLoaded", () => {
  let logoFileInput = document.getElementById("eventLogoFile");
  let eventCode = document.getElementById("eventCode").value;

  logoFileInput.addEventListener("change", event => {
    let file = event.target.files[0];

    if (!file) {
      alert("No file selected");
      return null;
    }

    if (file.type.indexOf("image") === -1) {
      alert("File must be an image.");
      event.target.value = "";
      return null;
    }

    if (file.size > 10485760) {
      alert("File size must not exceed 10MB.");
      event.target.value = "";
      return null;
    }

    let reader = new FileReader();
    reader.readAsDataURL(file);
    reader.addEventListener("loadend", () => {
      let image = new Image();
      image.src = reader.result;

      image.addEventListener("load", () => {
        // if (image.height > 512 || image.width > 512) {
        //   alert("Image dimensions must not exceed 512x512px.");
        //   event.target.value = "";
        //   return null;
        // }

        getS3Request(file, eventCode);
      });

    });
  });
});