function g = imageRotate(f,theta,mode)
    xMax=size(f,1);
    yMax=size(f,2);
    translate1= [   1           0               -xMax/2;
                    0           1               -yMax/2;
                    0           0               1   ]
    translate2= [   1           0               xMax/2;
                    0           1               yMax/2;
                    0           0               1   ]
    rotate=     [   cosd(theta) sind(theta)    0;
                    -sind(theta) cosd(theta)     0;
                    0           0               1   ]
    
    for x=1:xMax
        for y=1:yMax
            color=f(x,y);
            origCoord=  [   x;
                            y;
                            1   ];
            newCoord=translate2*rotate*translate1*origCoord;
            newCoord=round(newCoord);
            x1=newCoord(1);
            y1=newCoord(2);
            if(x1>0&&x1<xMax&&y1>0&&y1<yMax)
                g(x1,y1)=color;
        end
    end
    imshow(g)
end

