# Butgetin

## Overview Machine Learning
The Machine Learning part of this application are to do budgeting suggestion to user. We create a model for budgeting suggestion where the data transaction will be training in every beginning of the month, users will be input in the category like Transportation, Food& Drink, Digital, Vehicle, and etc then will be processed by the model.

## Dataset
The dataset we use in this model in first time is synthetic or dummy data but based on reality, the dataset incluide information in each column like user_id, date, category, amount and type.

For more details and access to the dataset used in training the machine learning models, please visit the following
link : https://www.kaggle.com/datasets/muammarfarhan/daily-transactions-in-3-month-period-dataset

Feel free to explore the dataset and utilize it for your own analyses or experiments.

## Model Architecture

•	Input Layer:
Accepts input shaped to match the number of numeric features in the dataset.  
•	Hidden Layers:  
o	Dense layer with 256 units, ReLU activation, and L2 regularization.  
o	Dropout layer with a rate of 0.3.  
o	Dense layer with 128 units, ReLU activation, and L2 regularization.  
o	Dropout layer with a rate of 0.2.  
o	Dense layer with 64 units, ReLU activation, and L2 regularization.  
o	Dropout layer with a rate of 0.1.  
•	Output Layer:
A dense layer that outputs a single value using a linear activation function.  

Model Summary  
![ModelSummary](https://github.com/user-attachments/assets/e56c6c0f-1391-459a-b861-a2d274fc6948)

Model Plot Training  
![PlotTraining](https://github.com/user-attachments/assets/2ce8c5ca-92ff-4c56-8656-4c1356daf536)

Model Suggest Budgeting  
![ModelSuggest](https://github.com/user-attachments/assets/e2a6afdb-62b1-4c0e-805f-46ee6e46ad3c)


## How to use our projects
•	Clone this repository or you can download the dataset  
•	Install the required libraries  
•	Pre-process the data  
•	Build and compile the model with the architectures as mentioned above  
•	Convert the model to .h5 format  
